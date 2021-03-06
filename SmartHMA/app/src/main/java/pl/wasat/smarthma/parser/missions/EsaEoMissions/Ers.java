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

package pl.wasat.smarthma.parser.missions.EsaEoMissions;

import android.content.Context;

import java.util.ArrayList;

import pl.wasat.smarthma.parser.Parser.BaseParser;
import pl.wasat.smarthma.parser.Parser.Pair;
import pl.wasat.smarthma.parser.missions.MissionInterface;
import pl.wasat.smarthma.parser.model.Mission;
import pl.wasat.smarthma.parser.model.Page;

/**
 * Created by marcel on 2015-08-11 00:09.
 * Part of the project  SmartHMA
 */
public class Ers extends BaseParser implements MissionInterface {

    private final static int MISSION_ID = 9;
    private final static String TITLE = "Ers";
    /**
     * The Ra.
     */
    final String RA = "https://earth.esa.int/web/guest/missions/esa-operational-eo-missions/ers/instruments/ra";
    /**
     * The Atsr.
     */
    final String ATSR = "https://earth.esa.int/web/guest/missions/esa-operational-eo-missions/ers/instruments/atsr";
    /**
     * The Gome.
     */
    final String GOME = "https://earth.esa.int/web/guest/missions/esa-operational-eo-missions/ers/instruments/gome";
    /**
     * The Mwr.
     */
    final String MWR = "https://earth.esa.int/web/guest/missions/esa-operational-eo-missions/ers/instruments/mwr";
    /**
     * The Sar.
     */
    final String SAR = "https://earth.esa.int/web/guest/missions/esa-operational-eo-missions/ers/instruments/sar";
    /**
     * The Ws.
     */
    final String WS = "https://earth.esa.int/web/guest/missions/esa-operational-eo-missions/ers/instruments/ws";
    /**
     * The Prare.
     */
    final String PRARE = "https://earth.esa.int/web/guest/missions/esa-operational-eo-missions/ers/instruments/prare";

    /**
     * Instantiates a new Ers.
     *
     * @param pageUrl the page url
     * @param context the context
     */
    public Ers(String pageUrl, Context context) {
        super(pageUrl, context);
        parserDb.addMission(new Mission(MISSION_ID, EsaEoMissions.CATEGORY_ID, TITLE));

    }

    @Override
    public void mainContent() {
        int exclude = 1;
        int ITEMS_COUNT = 1;
        ArrayList<Pair> list = super.getComplexPage(ITEMS_COUNT, exclude);
        for (Pair item : list) {
            parserDb.addPage(new Page(EsaEoMissions.CATEGORY_ID, MISSION_ID, (String) item.title, (String) item.content));
        }
    }

    @Override
    public void news() {
    }

    @Override
    public void milestones() {
    }

    @Override
    public void faq() {
    }

    @Override
    public void other() {
    }

    @Override
    public void overview() {

    }

    @Override
    public void objectives() {

    }

    @Override
    public void satellite() {

    }

    @Override
    public void groundSegment() {
        Pair pair = super.getSimplePage(GROUND_SEGMENT);
        parserDb.addPage(new Page(EsaEoMissions.CATEGORY_ID, MISSION_ID, (String) pair.title, (String) pair.content));
    }

    @Override
    public void instruments() {
        Pair pair = super.getSimplePage(INSTRUMENTS);
        parserDb.addPage(new Page(EsaEoMissions.CATEGORY_ID, MISSION_ID, (String) pair.title, (String) pair.content));
    }

    @Override
    public void scientificRequirements() {

    }

    @Override
    public void operations() {
        Pair pair = super.getSimplePage(OPERATIONS);
        parserDb.addPage(new Page(EsaEoMissions.CATEGORY_ID, MISSION_ID, (String) pair.title, (String) pair.content));
    }

    @Override
    public void applications() {

    }

    @Override
    public void science() {

    }

    @Override
    public void history() {

    }

    @Override
    public void industry() {

    }

}
