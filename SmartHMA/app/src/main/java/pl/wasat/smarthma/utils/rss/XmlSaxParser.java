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

import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import pl.wasat.smarthma.model.dc.Dc;
import pl.wasat.smarthma.model.entry.Entry;
import pl.wasat.smarthma.model.feed.Feed;
import pl.wasat.smarthma.model.iso.MDMetadata;
import pl.wasat.smarthma.model.om.EarthObservation;

/**
 * Created by Daniel on 2015-10-12.
 * This file is a part of module SmartHMA project.
 */
public class XmlSaxParser {

    /**
     * Parse feed feed.
     *
     * @param inputStreamFeed the input stream feed
     * @return the feed
     * @throws IOException the io exception
     */
    public Feed parseFeed(InputStream inputStreamFeed) throws IOException {
        long startTime = System.currentTimeMillis();
        FeedDataHandler feedDataHandler = null;
        try {
            startTime = System.currentTimeMillis();
            String inputString = IOUtils.toString(inputStreamFeed);

            Log.i("TO_STRING_TIME", String.valueOf(System.currentTimeMillis() - startTime));

            startTime = System.currentTimeMillis();
            XMLReader xrFeed = defineXmlSaxParser();

            InputSource inputSource = new InputSource(new StringReader(inputString));
            feedDataHandler = new FeedDataHandler(inputString);
            xrFeed.setContentHandler(feedDataHandler);

            startTime = System.currentTimeMillis();
            xrFeed.parse(inputSource);

        } catch (SAXException e) {
            Log.e("RSS Handler SAX", e.toString());
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            Log.e("RSS Parser Config", e.toString());
        }
        Log.i("PARSE_FEED", String.valueOf(System.currentTimeMillis() - startTime));
        //noinspection ConstantConditions
        return feedDataHandler.getFeeds();
    }

    private XMLReader defineXmlSaxParser() throws ParserConfigurationException, SAXException {
        SAXParserFactory spfFeed = SAXParserFactory.newInstance();
        SAXParser spFeed = spfFeed.newSAXParser();
        return spFeed.getXMLReader();
    }

    /**
     * Parse om feed feed.
     *
     * @param inputStreamFeed the input stream feed
     * @return the feed
     */
    public Feed parseOMFeed(InputStream inputStreamFeed) {
        OMDataHandler omDataHandler = new OMDataHandler();
        omDataHandler = (OMDataHandler) processFeedHandler(inputStreamFeed, omDataHandler);
        return omDataHandler.getFeeds();
    }

    private DefaultHandler processFeedHandler(InputStream inputStream, DefaultHandler feedHandler) {
        try {
            long startTime = System.currentTimeMillis();
            XMLReader xr = defineXmlSaxParser();
            xr.setContentHandler(feedHandler);

            InputSource inSource = new InputSource(inputStream);
            xr.parse(inSource);

            Log.i("PARSE_FEED", String.valueOf(System.currentTimeMillis() - startTime));
        } catch (SAXException e) {
            Log.e("RSS Handler SAX", e.toString());
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            Log.e("RSS Parser Config", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return feedHandler;
    }

    /**
     * Parse iso feed feed.
     *
     * @param inputStreamFeed the input stream feed
     * @return the feed
     */
    public Feed parseISOFeed(InputStream inputStreamFeed) {
        ISODataHandler isoDataHandler = new ISODataHandler();
        isoDataHandler = (ISODataHandler) processFeedHandler(inputStreamFeed, isoDataHandler);
        return isoDataHandler.getFeeds();
    }

    /**
     * Parse om metadata.
     *
     * @param entry the entry
     */
    public void parseOMMetadata(Entry entry) {
        EarthObservation eo = parseOMMetadata(entry.getRawMetadata());
        entry.setEarthObservation(eo);
        //return entry;
    }

    private EarthObservation parseOMMetadata(String metadata) {
        OMMetadataHandler omMetadataHandler = new OMMetadataHandler();
        omMetadataHandler = (OMMetadataHandler) processMetadataHandler(metadata, omMetadataHandler);
        return omMetadataHandler.getOMMetadata();
    }

    private DefaultHandler processMetadataHandler(String metadata, DefaultHandler metadataHandler) {
        try {
            long startTime = System.currentTimeMillis();
            XMLReader xrFeed = defineXmlSaxParser();

            InputSource inputSource = new InputSource(new StringReader(metadata));
            xrFeed.setContentHandler(metadataHandler);

            xrFeed.parse(inputSource);
            Log.i("PARSE_META", String.valueOf(System.currentTimeMillis() - startTime));
        } catch (SAXException e) {
            Log.e("RSS Handler SAX", e.toString());
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            Log.e("RSS Parser Config", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return metadataHandler;
    }

    /**
     * Parse iso metadata.
     *
     * @param entry the entry
     */
    public void parseISOMetadata(Entry entry) {
        MDMetadata mdMetadata = parseISOMetadata(entry.getRawMetadata());
        entry.setMDMetadata(mdMetadata);
        //return entry;
    }

    private MDMetadata parseISOMetadata(String metadata) {
        ISOMetadataHandler isoMetadataHandler = new ISOMetadataHandler();
        isoMetadataHandler = (ISOMetadataHandler) processMetadataHandler(metadata, isoMetadataHandler);
        return isoMetadataHandler.getMdMetadata();
    }

    /**
     * Parse dc metadata.
     *
     * @param entry the entry
     */
    public void parseDCMetadata(Entry entry) {
        Dc dcMetadata = parseDCMetadata(entry.getRawMetadata());
        entry.setDc(dcMetadata);
        //return entry;
    }

    private Dc parseDCMetadata(String metadata) {
        DCMetadataHandler dcMetadataHandler = new DCMetadataHandler();
        dcMetadataHandler = (DCMetadataHandler) processMetadataHandler(metadata, dcMetadataHandler);
        return dcMetadataHandler.getDC();
    }
}
