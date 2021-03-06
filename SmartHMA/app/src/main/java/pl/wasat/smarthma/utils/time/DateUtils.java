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

package pl.wasat.smarthma.utils.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pl.wasat.smarthma.R;
import pl.wasat.smarthma.SmartHMApplication;
import pl.wasat.smarthma.model.iso.EntryISO;

/**
 * The type Date utils.
 */
public class DateUtils {

    /**
     * Gets date difference.
     *
     * @param thenDate the then date
     * @return the date difference
     */
    public static String getDateDifference(Date thenDate) {
        Calendar now = Calendar.getInstance();
        Calendar then = Calendar.getInstance();
        now.setTime(new Date());
        then.setTime(thenDate);

        // Get the represented date in milliseconds
        long nowMs = now.getTimeInMillis();
        long thenMs = then.getTimeInMillis();

        // Calculate difference in milliseconds
        long diff = nowMs - thenMs;

        // Calculate difference in seconds
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if (diffMinutes < 60) {
            if (diffMinutes == 1)
                return diffMinutes + SmartHMApplication.getAppContext().getString(R.string.minute_ago);
            else
                return diffMinutes + SmartHMApplication.getAppContext().getString(R.string.minutes_ago);
        } else if (diffHours < 24) {
            if (diffHours == 1)
                return diffHours + SmartHMApplication.getAppContext().getString(R.string.hour_ago);
            else
                return diffHours + SmartHMApplication.getAppContext().getString(R.string.hours_ago);
        } else if (diffDays < 30) {
            if (diffDays == 1)
                return diffDays + SmartHMApplication.getAppContext().getString(R.string.day_ago);
            else
                return diffDays + SmartHMApplication.getAppContext().getString(R.string.days_ago);
        } else {
            return SmartHMApplication.getAppContext().getString(R.string.long_time_ago);
        }
    }

    /**
     * Gets iso pub date.
     *
     * @param entry the entry
     * @return the iso pub date
     */
    public static String getISOPubDate(EntryISO entry) {
        String date = "";
        try {
            if (entry.getDate() != null) {
                if (entry.getDate().getCIDate() != null && entry.getDate().getCIDate().getDateInCIDate().getDateGco() != null) {
                    date = entry.getDate().getCIDate().getDateInCIDate().getDateGco().getText();
                } else if (entry.getDate().getCIDate() == null) {
                    date = entry.getDate().toString();
                } else {
                    date = "1970-01-01";
                }
            } else if (entry.getMDMetadata() != null) {
                date = entry.getMDMetadata().getDateStamp().getDateGco().getText();
            } else if (date.isEmpty()) {
                date = "1970-01-01";
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Calendar to date string string.
     *
     * @param cal - Calendar
     * @return - String with date
     */
    public static String calendarToDateString(Calendar cal) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        return df.format(cal.getTime());
    }

    /**
     * Calendar to time string string.
     *
     * @param cal - Calendar
     * @return - String of time
     */
    public static String calendarToTimeString(Calendar cal) {
        SimpleDateFormat dfTime = new SimpleDateFormat("HH:mm:ss", Locale.UK);
        return dfTime.format(cal.getTime());
    }

    /**
     * Calendar to iso string.
     *
     * @param cal the cal
     * @return the string
     */
    public static String calendarToISO(Calendar cal) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
                Locale.UK);
        return df.format(cal.getTime());
    }

    /**
     * Timestamp to date time str string.
     *
     * @param timestamp the timestamp
     * @return the string
     */
    public static String timestampToDateTimeStr(long timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault()).format(new Date(timestamp));
    }
}
