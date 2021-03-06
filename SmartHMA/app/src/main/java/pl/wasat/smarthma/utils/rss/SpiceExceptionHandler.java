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

package pl.wasat.smarthma.utils.rss;

import android.content.Context;
import android.util.Log;

import com.google.api.client.http.HttpResponseException;
import com.octo.android.robospice.persistence.exception.SpiceException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import pl.wasat.smarthma.R;
import pl.wasat.smarthma.preferences.GlobalPreferences;
import pl.wasat.smarthma.preferences.SharedPrefs;
import pl.wasat.smarthma.utils.io.FilesWriter;
import pl.wasat.smarthma.utils.time.DateUtils;

/**
 * Created by Daniel on 2015-09-22 21:49.
 * Part of the project  SmartHMA
 */
public class SpiceExceptionHandler {
    private final SpiceException spiceException;
    private final Context context;
    private String exTextMessage;
    private String exRawMessage;
    private String exRawCause;

    /**
     * Instantiates a new Spice exception handler.
     *
     * @param context        the context
     * @param spiceException the spice exception
     */
    public SpiceExceptionHandler(Context context, SpiceException spiceException) {
        this.context = context;
        this.spiceException = spiceException;
    }

    /**
     * Invoke.
     */
    public void invoke() {
        if (spiceException == null) {
            exTextMessage = context.getString(R.string.unknown_exception);
            exRawMessage = context.getString(R.string.wrong_body);
            exRawCause = context.getString(R.string.wrong_request_status);
        } else if (spiceException.getCause() instanceof HttpResponseException) {
            exRawMessage = spiceException.getMessage();
            exRawCause = spiceException.getCause().toString();
            FedeoExceptionHandler fedHr = null;
            try {
                String inStr;
                HttpResponseException exception = (HttpResponseException) spiceException
                        .getCause();
                inStr = exception.getContent();

                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser sp = spf.newSAXParser();
                XMLReader xr = sp.getXMLReader();

                fedHr = new FedeoExceptionHandler();

                xr.setContentHandler(fedHr);
                InputSource inputSource = new InputSource(new StringReader(
                        inStr));
                xr.parse(inputSource);

            } catch (IOException e) {
                Log.e("RSS Handler IO", e.toString());
            } catch (SAXException e) {
                Log.e("RSS Handler SAX", e.toString());
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                Log.e("RSS Parser Config", e.toString());
            }

            assert fedHr != null;
            exTextMessage = fedHr.getFedeoException().getExceptionReport()
                    .getException().getExceptionText().getText();

        } else if (spiceException.getCause() == null) {
            exTextMessage = context.getString(R.string.wide_area_or_timespan);
            exRawMessage = spiceException.getMessage();
            exRawCause = context.getString(R.string.none);
        } else {
            exTextMessage = context.getString(R.string.wide_area_or_timespan);
            exRawMessage = spiceException.getMessage();
            exRawCause = spiceException.getCause().toString();
        }
        writeExceptionToLog();
    }

    private void writeExceptionToLog() {
        GlobalPreferences globalPreferences = new GlobalPreferences(context);
        if (globalPreferences.getIsDebugMode()) {
            FilesWriter filesWriter = new FilesWriter();
            filesWriter.appendLogToFile(buildExceptionLog(), "log_errors.txt");
        }
    }

    private String buildExceptionLog() {
        String NEW_LINE = System.getProperty("line.separator");
        String timestamp = DateUtils.timestampToDateTimeStr(System.currentTimeMillis());
        String url = obtainFormattedUrl().replaceAll(NEW_LINE, "");
        if (exRawCause == null) exRawCause = "";
        return timestamp + context.getString(R.string.url) + url + NEW_LINE +
                timestamp + context.getString(R.string.exception) + exRawCause.replaceAll(NEW_LINE + NEW_LINE, NEW_LINE) +
                NEW_LINE + NEW_LINE;
    }

    private String obtainFormattedUrl() {
        SharedPrefs sharedPrefs = new SharedPrefs(context);
        return sharedPrefs.getUrlPrefs();
    }

    /**
     * Gets ex text message.
     *
     * @return the ex text message
     */
    public String getExTextMessage() {
        return exTextMessage;
    }

    /**
     * Gets ex raw message.
     *
     * @return the ex raw message
     */
    public String getExRawMessage() {
        return exRawMessage;
    }

    /**
     * Gets ex raw cause.
     *
     * @return the ex raw cause
     */
    public String getExRawCause() {
        return exRawCause;
    }
}
