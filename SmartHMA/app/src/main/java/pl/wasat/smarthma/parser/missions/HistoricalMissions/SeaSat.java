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

package pl.wasat.smarthma.parser.missions.HistoricalMissions;

import android.content.Context;

import java.util.ArrayList;

import pl.wasat.smarthma.parser.Parser.BaseParser;
import pl.wasat.smarthma.parser.Parser.Pair;
import pl.wasat.smarthma.parser.missions.SimpleMissionInterface;
import pl.wasat.smarthma.parser.model.Mission;
import pl.wasat.smarthma.parser.model.Page;

/**
 * Created by marcel on 2015-08-13 00:09.
 * Part of the project  SmartHMA
 */
public class SeaSat extends BaseParser implements SimpleMissionInterface {
    private final static int MISSION_ID = 52;
    private final static String TITLE = "SeaSat";

    /**
     * Instantiates a new Sea sat.
     *
     * @param pageUrl the page url
     * @param context the context
     */
    public SeaSat(String pageUrl, Context context) {
        super(pageUrl, context);
        parserDb.addMission(new Mission(MISSION_ID, HistoricalMissions.CATEGORY_ID, TITLE));
    }

    @Override
    public void mainContent() {
        int ITEMS_COUNT = 2;
        ArrayList<Pair> list = super.getComplexPage(ITEMS_COUNT);
        for (Pair item : list) {
            parserDb.addPage(new Page(HistoricalMissions.CATEGORY_ID, MISSION_ID, (String) item.title, (String) item.content));
        }
    }
}
