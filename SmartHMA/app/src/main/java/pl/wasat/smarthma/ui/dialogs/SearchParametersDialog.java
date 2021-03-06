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

package pl.wasat.smarthma.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import pl.wasat.smarthma.R;
import pl.wasat.smarthma.database.SearchHistory;
import pl.wasat.smarthma.ui.activities.SearchActivity;

/**
 * Handles user interaction related to search history management.
 */
public class SearchParametersDialog extends DialogFragment {
    private SearchActivity activity;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.search_history_item)
                .setItems(R.array.search_modes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Log.d("ZX", which + "");
                        try {
                            if (which == 5) {
                                new SearchHistory(getActivity()).clearHistory();
                            } else {
                                SearchParametersResultsDialog newFragment = new SearchParametersResultsDialog();
                                newFragment.setParameters(activity, which);
                                newFragment.show(getFragmentManager(), "Search_Parameters_Results");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        return builder.create();
    }

    /**
     * Sets activity.
     *
     * @param activity the activity
     */
    public void setActivity(SearchActivity activity) {
        this.activity = activity;
    }
}
