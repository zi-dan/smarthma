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

package pl.wasat.smarthma.ui.frags.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

import pl.wasat.smarthma.R;
import pl.wasat.smarthma.customviews.SmHmaTimePickerDialog;
import pl.wasat.smarthma.customviews.TimePicker;
import pl.wasat.smarthma.helper.Const;

/**
 * Created by Daniel on 2015-07-11 00:46.
 * Part of the project  SmartHMA
 */
public class TimePickerFragment extends DialogFragment implements
        SmHmaTimePickerDialog.OnTimeSetListener {

    private String timeViewTag;
    private Calendar calendar;
    private OnTimePickerFragmentListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = context instanceof Activity ? (Activity) context : null;
        try {
            mListener = (OnTimePickerFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + activity.getString(R.string.must_implement)
                    + OnTimePickerFragmentListener.class.getSimpleName());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (getArguments() != null) {
            timeViewTag = getArguments().getString(Const.KEY_DATE_PICKER_DT_VIEW_TAG);
            calendar = (Calendar) getArguments().getSerializable(Const.KEY_DATE_TIME_PICKER_CALENDAR);
        }
        assert calendar != null;
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        // Create a new instance of TimePickerDialog and return it
        return new SmHmaTimePickerDialog(getActivity(), this, hour, minute,
                second, true);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ikovac.timepickerwithseconds.view.MyTimePickerDialog.
     * OnTimeSetListener
     * #onTimeSet(com.ikovac.timepickerwithseconds.view.TimePicker, int,
     * int, int)
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute,
                          int seconds) {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute, seconds);
        mListener.onTimePickerFragmentTimeChoose(calendar, timeViewTag);
    }

    /**
     * The interface On time picker fragment listener.
     */
    public interface OnTimePickerFragmentListener {
        /**
         * On time picker fragment time choose.
         *
         * @param calendar the calendar
         * @param viewTag  the view tag
         */
        void onTimePickerFragmentTimeChoose(Calendar calendar, String viewTag);
    }
}
