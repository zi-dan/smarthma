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

package pl.wasat.smarthma.model.om;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pl.wasat.smarthma.utils.obj.LatLngExt;
import pl.wasat.smarthma.utils.text.SmartHMAStringStyle;

/**
 * The type Line string.
 */
public class LineString implements Serializable {

    private static final long serialVersionUID = 1L;

    private String _prefix;
    private Coordinates coordinates;
    private String _gml_id;
    private String srsName;
    private PosString posString;
    private List<Pos> posList = new ArrayList<>();


    /**
     * Gets prefix.
     *
     * @return the prefix
     */
    public String get_prefix() {
        return _prefix;
    }

    /**
     * Sets prefix.
     *
     * @param _prefix the prefix
     */
    public void set_prefix(String _prefix) {
        this._prefix = _prefix;
    }

    /**
     * Gets coordinates.
     *
     * @return the coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Sets coordinates.
     *
     * @param coordinates the coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Gets gml id.
     *
     * @return the gml id
     */
    public String get_gml_id() {
        return _gml_id;
    }

    /**
     * Sets gml id.
     *
     * @param _gml_id the gml id
     */
    public void set_gml_id(String _gml_id) {
        this._gml_id = _gml_id;
    }

    /**
     * Gets srs name.
     *
     * @return the srs name
     */
    public String getSrsName() {
        return srsName;
    }

    /**
     * Sets srs name.
     *
     * @param srsName the srs name
     */
    public void setSrsName(String srsName) {
        this.srsName = srsName;
    }

    /**
     * Gets pos string.
     *
     * @return the pos string
     */
    public PosString getPosString() {
        return posString;
    }

    /**
     * Sets pos string.
     *
     * @param posString the pos string
     */
    public void setPosString(PosString posString) {
        if (posString == null) {
            posString = new PosString();
            posString.setPointsString("");
        }
        this.posString = posString;

        if (!posString.getPointsString().isEmpty()
                && posString.getPointsString().length() < 200) {
            this.posList = setPosList(posString.getPointsString());
        }
    }

    /**
     * Gets pos list.
     *
     * @return the pos list
     */
    public List<Pos> getPosList() {
        return posList;
    }

    private void setPosList(List<Pos> posList) {
        if (!posList.isEmpty()) {
            this.posList = posList;
        }
    }

    /**
     * Sets pos list.
     *
     * @param pointsString the points string
     * @return the pos list
     */
    public List<Pos> setPosList(String pointsString) {
        String[] coorStr = pointsString.replaceAll("  ", " ").split(" ");
        List<Pos> latLngPosList = new ArrayList<>();

        for (int j = 0; j < coorStr.length - 1; j = j + 2) {
            LatLngExt ftPt = new LatLngExt(Double.valueOf(coorStr[j]),
                    Double.valueOf(coorStr[j + 1]));
            Pos pos = new Pos();
            pos.setLatLng(ftPt);
            latLngPosList.add(pos);
        }
        this.setPosList(latLngPosList);
        return latLngPosList;
    }

    @Override
    public String toString() {
        ToStringStyle style = new SmartHMAStringStyle();
        ToStringBuilder.setDefaultStyle(style);
        return ToStringBuilder.reflectionToString(this, style);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }


}
