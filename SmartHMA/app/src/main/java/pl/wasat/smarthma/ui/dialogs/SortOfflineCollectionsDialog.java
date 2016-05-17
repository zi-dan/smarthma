package pl.wasat.smarthma.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.List;

import pl.wasat.smarthma.R;
import pl.wasat.smarthma.SmartHMApplication;
import pl.wasat.smarthma.helper.Const;
import pl.wasat.smarthma.helper.DataSorter;
import pl.wasat.smarthma.model.iso.EntryISO;
import pl.wasat.smarthma.ui.activities.FavouriteCollectionsActivity;
import pl.wasat.smarthma.ui.frags.search.SearchListFragmentOffline;

/**
 * Handles user interaction related to sorting data.
 */
public class SortOfflineCollectionsDialog extends DialogFragment {
    private FavouriteCollectionsActivity activity;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.action_sort)
                .setItems(R.array.sorting_modes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            if (which == 0) {
                                SmartHMApplication.sortingType = Const.SORT_BY_TITLE_ASCENDING;
                            } else if (which == 1) {
                                SmartHMApplication.sortingType = Const.SORT_BY_TITLE_DESCENDING;
                            } else if (which == 2) {
                                SmartHMApplication.sortingType = Const.SORT_BY_DATE_ASCENDING;
                            } else if (which == 3) {
                                SmartHMApplication.sortingType = Const.SORT_BY_DATE_DESCENDING;
                            }
                            DataSorter sorter = new DataSorter();
                            SearchListFragmentOffline searchListFrag = activity.getListFragment();
                            List<EntryISO> entries = searchListFrag.getEntries();
                            sorter.sort(entries);
                            activity.refreshList();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void setActivity(FavouriteCollectionsActivity activity) {
        this.activity = activity;
    }
}