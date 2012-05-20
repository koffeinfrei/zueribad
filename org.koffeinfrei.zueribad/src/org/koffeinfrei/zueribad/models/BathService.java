/*
 *  Koffeinfrei Zueribad
 *  Copyright (C) 2011  Alexis Reigel
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.koffeinfrei.zueribad.models;

import android.graphics.drawable.Drawable;
import com.google.android.maps.GeoPoint;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.koffeinfrei.zueribad.R;
import org.koffeinfrei.zueribad.config.Constants;
import org.koffeinfrei.zueribad.utils.AndroidI18nException;
import org.koffeinfrei.zueribad.utils.BetterDate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;

public class BathService {
    private URI remoteUrl;
    private String remoteXmlData;
    private String[] staticXmlData;
    private Hashtable<Integer, Bath> baths;
    private URI uvIndexImageUrl;
    private Drawable uvIndexImage;

    public BathService(String remoteUrl, String...staticXmlData) throws AndroidI18nException {
        this.staticXmlData = staticXmlData;
        try {
            this.remoteUrl = new URI(remoteUrl);
        } catch (URISyntaxException e) {
            throw new AndroidI18nException(R.string.error_url, e);
        }
        baths = new Hashtable<Integer, Bath>();
    }

    public Hashtable<Integer, Bath> load() throws AndroidI18nException {
        download();

        parseRemoteXml();
        parseStaticXml();

        return baths;
    }

    public Drawable getUvIndexImage() {
        if (uvIndexImage == null){
            downloadUvIndexImage();
        }
        return uvIndexImage;
    }

    private void downloadUvIndexImage() {
        try {
            if (uvIndexImageUrl != null){
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(uvIndexImageUrl);
                get.addHeader("Accept", "text/xml");
                get.addHeader("User-Agent", "Koffeinfrei.Zueribad");

                HttpResponse response = client.execute(get);
                HttpEntity entity = response.getEntity();
                if (entity != null){
                    InputStream inputStream = entity.getContent();
                    uvIndexImage = Drawable.createFromStream(inputStream, "src");
                }
            }
        } catch (IOException e) {
            // this is a minor problem, we just skip the uv index picture and
            // go on with our lives
        }
    }

    private void parseRemoteXml() throws AndroidI18nException {
        Document doc = getXmlDocument(remoteXmlData);

        Element rootElement = doc.getDocumentElement();
        if (rootElement == null || !rootElement.getNodeName().equals("bathinfos")){
            throw new AndroidI18nException(R.string.error_parsedata);
        }

        // get all baths
        NodeList bathNodes = doc.getElementsByTagName("bath");
        for(int i = 0; i < bathNodes.getLength(); ++i){
            Element bathElement = (Element)bathNodes.item(i);

            Bath bath = new Bath(i);

            bath.setName(getElementStringValue(bathElement, "title"));
            bath.setTemperature(getElementDoubleValue(bathElement, "temperatureWater"));
            bath.setModified(getElementDateValue(bathElement, "dateModified", Constants.DATE_FORMAT_CUSTOM, 4));
            bath.setStatus(getElementStringValue(bathElement, "openClosedTextPlain"));
            bath.setUrl(getElementStringValue(bathElement, "urlPage"));

            baths.put(i, bath);
        }

        // get global info
        NodeList uvIndexImgUrlNode = doc.getElementsByTagName("meta");
        if (uvIndexImgUrlNode != null && uvIndexImgUrlNode.getLength() > 0){
            uvIndexImageUrl = getElementUriValue((Element) uvIndexImgUrlNode.item(0), "uvindexImgUrl");
        }
    }

    private void parseStaticXml() throws AndroidI18nException {
        for(String data : staticXmlData){
            Document doc = getXmlDocument(data);

            // get all baths
            NodeList bathNodes = doc.getElementsByTagName("bath");
            for(int i = 0; i < bathNodes.getLength(); ++i){
                Element bathElement = (Element)bathNodes.item(i);

                String title = getElementStringValue(bathElement, "title");
                Double geoX = getElementDoubleValue(bathElement, "geoPointX");
                Double geoY = getElementDoubleValue(bathElement, "geoPointY");
                String address = getElementStringValue(bathElement, "address");
                String address2 = getElementStringValue(bathElement, "address2");
                String route = getElementStringValue(bathElement,  "route");
                BetterDate seasonStart = getElementDateValue(bathElement, "seasonStart", Constants.DATE_FORMAT_DATEONLY, 0);
                BetterDate seasonEnd = getElementDateValue(bathElement, "seasonEnd", Constants.DATE_FORMAT_DATEONLY, 0);
                String openingHoursInfo = getElementStringValue(bathElement, "openingHoursInfo");
                String openingHoursWeather1 = getElementStringValue(bathElement, "openingHoursWeather1");
                String openingHoursWeather2 = getElementStringValue(bathElement, "openingHoursWeather2");
                String openingHoursWeather3 = getElementStringValue(bathElement, "openingHoursWeather3");

                for (Bath b : baths.values()){
                    if (b.getName().equals(title)){
                        if (geoX != null && geoY != null){
                            Integer x = (int)(geoX * Constants.GEO_POINT_MULTIPLIER);
                            Integer y = (int)(geoY * Constants.GEO_POINT_MULTIPLIER);
                            GeoPoint point = new GeoPoint(x, y);
                            b.setGeoPoint(point);
                        }
                        if (address != null){
                            b.setAddress(address);
                        }
                        if (address2 != null){
                            b.setAddress2(address2);
                        }
                        if (route != null){
                            b.setRoute(route);
                        }
                        if (seasonStart != null){
                            b.setSeasonStart(seasonStart);
                        }
                        if (seasonEnd != null){
                            b.setSeasonEnd(seasonEnd);
                        }
                        if (openingHoursInfo != null){
                            b.setOpeningHoursInfo(openingHoursInfo);
                        }
                        if (openingHoursWeather1 != null){
                        	b.setOpeningHoursWeather1(openingHoursWeather1);
                        }
                        if (openingHoursWeather2 != null){
                        	b.setOpeningHoursWeather2(openingHoursWeather2);
                        }
                        if (openingHoursWeather3 != null){
                        	b.setOpeningHoursWeather3(openingHoursWeather3);
                        }
                    }
                }
            }
        }
    }

    private Document getXmlDocument(String xmlData) throws AndroidI18nException {
        InputSource source = new InputSource(new StringReader(xmlData));

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new AndroidI18nException(R.string.error_parsedata, e);
        }

        Document doc;
        try {
            doc = docBuilder.parse(source);
        } catch (SAXException e) {
            throw new AndroidI18nException(R.string.error_parsedata, e);
        } catch (IOException e) {
            throw new AndroidI18nException(R.string.error_parsedata, e);
        }
        doc.getDocumentElement().normalize();
        return doc;
    }

    private String getElementStringValue(Element parent, String childName){
        Node node = parent.getElementsByTagName(childName).item(0);
        if (node == null){
            return null;
        }
        return node.getTextContent().trim();
    }

    private Double getElementDoubleValue(Element parent, String childName){
        String stringValue = getElementStringValue(parent, childName);
        if (stringValue == null){
            return null;
        }
        return Double.parseDouble(stringValue);
    }

    private BetterDate getElementDateValue(Element parent, String childName, String dateFormat, int parsePosition){
        String stringValue = getElementStringValue(parent, childName);
        if (stringValue == null){
            return null;
        }

        BetterDate date = BetterDate.parse(stringValue, dateFormat, parsePosition);
        return date == null ? new BetterDate() : date;
    }

    private URI getElementUriValue(Element parent, String childName){
        String stringValue = getElementStringValue(parent, childName);
        if (stringValue == null){
            return null;
        }
        try {
            URI url = new URI(stringValue);
            return url;
        } catch (URISyntaxException e) {
            // this is a minor problem, we just skip the uv index picture and
            // go on with our lives
            return null;
        }
    }

    private void download() throws AndroidI18nException {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(remoteUrl);
        get.addHeader("Accept", "text/xml");
        get.addHeader("User-Agent", "Koffeinfrei.Zueribad");

        try {
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            if (entity != null){
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder dataBuilder = new StringBuilder();
                String tmpData;
                while((tmpData = reader.readLine()) != null){
                    dataBuilder.append(tmpData);
                }

                remoteXmlData = dataBuilder.toString();
            }
        } catch (IOException e) {
            throw new AndroidI18nException(R.string.error_download,e);
        }
    }
}
