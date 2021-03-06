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

package pl.wasat.smarthma.parser.missions;

/**
 * Created by marcel paduch on 2015-08-10 00:09.
 * Part of the project  SmartHMA
 */
public interface MissionInterface {

    /**
     * Main content.
     */
    void mainContent();

    /**
     * News.
     */
    void news();

    /**
     * Milestones.
     */
    void milestones();

    /**
     * Faq.
     */
    void faq();

    /**
     * Other.
     */
    void other();

    /**
     * Overview.
     */
    void overview();

    /**
     * Objectives.
     */
    void objectives();

    /**
     * Satellite.
     */
    void satellite();

    /**
     * Ground segment.
     */
    void groundSegment();

    /**
     * Instruments.
     */
    void instruments();

    /**
     * Scientific requirements.
     */
    void scientificRequirements();

    /**
     * Operations.
     */
    void operations();

    /**
     * Applications.
     */
    void applications();

    /**
     * Science.
     */
    void science();

    /**
     * History.
     */
    void history();

    /**
     * Industry.
     */
    void industry();


}
