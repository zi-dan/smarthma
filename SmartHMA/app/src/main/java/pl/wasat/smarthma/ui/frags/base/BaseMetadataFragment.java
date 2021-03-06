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

package pl.wasat.smarthma.ui.frags.base;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import java.lang.reflect.Field;

import pl.wasat.smarthma.R;
import pl.wasat.smarthma.customviews.TextViewWithFont;

/**
 * Created by Daniel on 2015-10-04 17:27.
 * Part of the project  SmartHMA
 */
public class BaseMetadataFragment extends Fragment {
    /**
     * The Linear layout.
     */
    protected LinearLayout linearLayout;

    /**
     * Sets meta data views.
     *
     * @param headerText       the header text
     * @param eOMetaDataObject the e o meta data object
     */
    protected void setMetaDataViews(String headerText, Object eOMetaDataObject) {

        TextViewWithFont tvMetaHeader = defMetadataHeader(headerText);
        linearLayout.addView(tvMetaHeader);

        Class<?> c1 = eOMetaDataObject.getClass();
        Field[] fields = c1.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(eOMetaDataObject);
            } catch (IllegalAccessException | IllegalArgumentException e) {
                e.printStackTrace();
            }
            if (value != null) {
                addNewRowView(name, value.toString());
            }
        }

        View viewSepLine = defSeparatorLine(Color.rgb(0, 0, 0));
        linearLayout.addView(viewSepLine);
    }

    @NonNull
    private TextViewWithFont defMetadataHeader(String headerText) {
        int topPad = (int) getResources().getDimension(R.dimen.browse_eo_padding);
        int pad = (int) getResources().getDimension(R.dimen.browse_eo_padding_text);
        TextViewWithFont tvMetaHeader = new TextViewWithFont(getActivity(), null, R.style.textMetaHeader);
        //noinspection deprecation
        tvMetaHeader.setTextAppearance(getActivity(), R.style.textMetaHeader);
        tvMetaHeader.setPadding(pad, (int) (topPad * 1.5), pad, pad);
        tvMetaHeader.setText(headerText);
        return tvMetaHeader;
    }

    private void addNewRowView(String title, String value) {
        if (!title.equalsIgnoreCase("additionalProperties")
                && !title.equalsIgnoreCase("_gml_id")
                && !title.equalsIgnoreCase("serialVersionUID")
                && !value.isEmpty()) {

            int leftPad = (int) getResources().getDimension(R.dimen.browse_eo_padding_text);

            TextViewWithFont itemTvTitle = defTitleItemView(title, leftPad);
            TextViewWithFont itemTvValue = defValueItemView(value);
            LinearLayout itemLinearRow = defItemLayoutRow();

            itemLinearRow.addView(itemTvTitle);
            itemLinearRow.addView(itemTvValue);

            View viewSepLine = defSeparatorLine(Color.rgb(150, 150, 150));
            linearLayout.addView(itemLinearRow);
            linearLayout.addView(viewSepLine);
        }
    }

    @NonNull
    private View defSeparatorLine(int rgbColor) {
        int leftPad = (int) getResources().getDimension(R.dimen.browse_eo_padding_text);
        View viewSepLine = new View(getActivity());
        TableLayout.LayoutParams viewLp = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 1);
        viewLp.setMargins(leftPad, 0, 0, 0);
        viewSepLine.setLayoutParams(viewLp);
        viewSepLine.setPadding(leftPad, 0, 0, 0);
        viewSepLine.setBackgroundColor(rgbColor);
        return viewSepLine;
    }

    @NonNull
    private TextViewWithFont defTitleItemView(String title, int leftPad) {
        TextViewWithFont itemTvTitle = new TextViewWithFont(getActivity(), null, R.style.textMetaItemTitle);
        itemTvTitle.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT, 2f));
        //noinspection deprecation
        itemTvTitle.setTextAppearance(getActivity(), R.style.textMetaItemTitle);
        itemTvTitle.setPadding(leftPad, 0, 0, 0);
        itemTvTitle.setText(title);
        return itemTvTitle;
    }

    /**
     * Def value item view text view with font.
     *
     * @param value the value
     * @return the text view with font
     */
    @NonNull
    protected TextViewWithFont defValueItemView(String value) {
        TextViewWithFont itemTvValue = new TextViewWithFont(getActivity(), null, R.style.textMetaItemValue);
        itemTvValue.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
        //noinspection deprecation
        itemTvValue.setTextAppearance(getActivity(), R.style.textMetaItemValue);
        itemTvValue.setText(value);
        return itemTvValue;
    }

    @NonNull
    private LinearLayout defItemLayoutRow() {
        LinearLayout itemLinearRow = new LinearLayout(getActivity());
        itemLinearRow.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.MATCH_PARENT, 1f));
        int bottomPad = (int) getResources().getDimension(R.dimen.search_eo_data_padding_medium);
        itemLinearRow.setPadding(0, 0, 0, bottomPad);
        return itemLinearRow;
    }
}
