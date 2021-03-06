/*
 * Copyright (c) 2016.  SmartHMA ESA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.wasat.smarthma.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StatFs;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.webkit.URLUtil;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import pl.wasat.smarthma.R;
import pl.wasat.smarthma.helper.Const;
import pl.wasat.smarthma.utils.io.FilesWriter;
import pl.wasat.smarthma.utils.text.StringExt;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DownloadService extends IntentService {


    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {DriveScopes.DRIVE_FILE};
    private Builder mBuilder;
    private int action;
    private NotificationManager mNotifyManager;
    private boolean notEnoughSpace;

    private String productName;
    private String urlData;
    private String metadata;

    /**
     * Instantiates a new Download service.
     */
    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            action = intent.getIntExtra(Const.KEY_ACTION_CLOUD_DOWNLOAD_SERVICE, 0);
            productName = intent.getStringExtra(Const.KEY_INTENT_CLOUD_PRODUCT_NAME);
            urlData = intent.getStringExtra(Const.KEY_INTENT_CLOUD_PRODUCT_URL);
            metadata = intent.getStringExtra(Const.KEY_INTENT_CLOUD_PRODUCT_METADATA);
            downloadFile();
        }
    }

    private void downloadFile() {
        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle(getString(R.string.download))
                .setContentText(getString(R.string.download_in_progress))
                .setSmallIcon(R.mipmap.ic_launcher_circle);
        new DownloadFileAsync(action, productName, urlData, metadata).execute();
    }

    /**
     * The type Download file async.
     */
    class DownloadFileAsync extends AsyncTask<String, String, Boolean> {
        /**
         * The constant LOG_TAG.
         */
        public static final String LOG_TAG = "DOWNLOAD FILE";
        private final String productName;
        private final String urlData;
        private final String metadata;
        private final String DROPBOX_DIR = "/EO_DATA/";
        private final int action;
        private File dataFile;
        private String dataPath;

        /**
         * Instantiates a new Download file async.
         *
         * @param action      the action
         * @param productName the product name
         * @param urlData     the url data
         * @param metadata    the metadata
         */
        public DownloadFileAsync(int action, String productName, String urlData, String metadata) {
            this.action = action;
            this.productName = productName;
            this.urlData = urlData;
            this.metadata = metadata;
        }

        @Override
        protected Boolean doInBackground(String... aurl) {
            try {
                if (action == 0) {
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(DownloadService.this);
                    GoogleAccountCredential mCredential = GoogleAccountCredential.usingOAuth2(
                            getApplicationContext(), Arrays.asList(SCOPES))
                            .setBackOff(new ExponentialBackOff())
                            .setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));
                    HttpTransport transport = AndroidHttp.newCompatibleTransport();
                    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
                    Drive mService = new com.google.api.services.drive.Drive.Builder(
                            transport, jsonFactory, mCredential)
                            .setApplicationName("GoogleDriveSample")
                            .build();
                    mService.about().get().execute();
                }
                StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
                //noinspection deprecation
                long availableBlocks = stat.getAvailableBlocks();
                //noinspection deprecation
                long blockSizeInBytes = stat.getBlockSize();
                long freeSpaceInMegabytes = availableBlocks * (blockSizeInBytes);

                //connecting to url
                URL u = new URL(urlData);
                HttpURLConnection c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();

                int lengthOfFile = c.getContentLength();
                if (lengthOfFile > freeSpaceInMegabytes) {
                    notEnoughSpace = true;
                    return false;
                }
                dataPath = buildPath(Const.SMARTHMA_PATH_TEMP);
                FilesWriter.validateDir(dataPath);
                String fileName = URLUtil.guessFileName(urlData, null, null);
                dataFile = new File(dataPath, fileName);
                FileOutputStream f = new FileOutputStream(dataFile);

                InputStream in = c.getInputStream();

                byte[] buffer = new byte[1024];
                int len1;
                long total = 0;
                int progress = 0;
                while ((len1 = in.read(buffer)) > 0) {
                    total += len1;
                    if (progress != (int) ((total * 100) / lengthOfFile)) {
                        progress = (int) ((total * 100) / lengthOfFile);
                        publishProgress("" + progress);
                    }
                    f.write(buffer, 0, len1);
                }
                f.close();

            } catch (UserRecoverableAuthIOException e) {
                Intent userIntent = e.getIntent();
                userIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getBaseContext().startActivity(userIntent);
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(LOG_TAG, e.getMessage());
                return false;
            }
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mBuilder.setProgress(100, 0, false);
            mNotifyManager.notify(1, mBuilder.build());
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            if (success) {

                GoogleAccountCredential mCredential;
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(DownloadService.this);

                mCredential = GoogleAccountCredential.usingOAuth2(
                        getApplicationContext(), Arrays.asList(SCOPES))
                        .setBackOff(new ExponentialBackOff())
                        .setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));
                mBuilder.setContentText(getResources().getString(R.string.download_completed));
                mBuilder.setProgress(0, 0, false);
                mNotifyManager.notify(1, mBuilder.build());

                if (action == 0)
                    new GoogleDriveUpload(mCredential, DownloadService.this).execute();
                else {
                    FilesWriter filesWriter = new FilesWriter();

                    File urlFile = filesWriter.writeToFile(urlData, "eo.url", dataPath);
                    new DropboxUpload(DownloadService.this, buildPath(DROPBOX_DIR), urlFile).execute();

                    File metaFile = filesWriter.writeToFile(metadata, "metadata.xml", dataPath);
                    new DropboxUpload(DownloadService.this, buildPath(DROPBOX_DIR), metaFile).execute();

                    new DropboxUpload(DownloadService.this, buildPath(DROPBOX_DIR), dataFile).execute();
                }
            } else {
                if (notEnoughSpace) {
                    mBuilder.setContentText(getResources().getString(R.string.not_enough_memory));
                    mBuilder.setProgress(0, 0, false);
                    mNotifyManager.notify(1, mBuilder.build());
                } else {
                    mBuilder.setContentText(getResources().getString(R.string.error_downloading));
                    mBuilder.setProgress(0, 0, false);
                    mNotifyManager.notify(1, mBuilder.build());
                }
            }
        }

        protected void onProgressUpdate(String... progress) {
            super.onProgressUpdate(progress);
            mBuilder.setProgress(100, Integer.parseInt(progress[0]), false);
            mNotifyManager.notify(1, mBuilder.build());
        }

        private String buildPath(String dir) {
            return dir + StringExt.cleanDirName(productName) + "/";
        }
    }
}
