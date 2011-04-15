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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;

public class BathService {
    private URI remoteUrl;
    private String remoteXmlData;
    private String staticXmlData;
    private Hashtable<Integer, Bath> baths;
    private URI uvIndexImageUrl;
    private Drawable uvIndexImage;

    public BathService(String remoteUrl, String staticXmlData) throws AndroidI18nException {
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

        // get all baths
        NodeList bathNodes = doc.getElementsByTagName("bath");
        for(int i = 0; i < bathNodes.getLength(); ++i){
            Element bathElement = (Element)bathNodes.item(i);

            Bath bath = new Bath(i);

            bath.setName(getElementStringValue(bathElement, "title"));
            bath.setTemperature(getElementDoubleValue(bathElement, "temperatureWater"));
            bath.setModified(getElementDateValue(bathElement, "dateModified"));
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
        Document doc = getXmlDocument(staticXmlData);

        // get all baths
        NodeList bathNodes = doc.getElementsByTagName("bath");
        for(int i = 0; i < bathNodes.getLength(); ++i){
            Element bathElement = (Element)bathNodes.item(i);

            String title = getElementStringValue(bathElement, "title");
            int x = (int)(getElementDoubleValue(bathElement, "geoPointX") * 1000000.0);
            int y = (int)(getElementDoubleValue(bathElement, "geoPointY") * 1000000.0);
            GeoPoint point = new GeoPoint(x, y);

            for (Bath b : baths.values()){
                if (b.getName().equals(title)){
                    b.setGeoPoint(point);
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
        return parent.getElementsByTagName(childName).item(0).getTextContent();
    }

    private Double getElementDoubleValue(Element parent, String childName){
        String stringValue = getElementStringValue(parent, childName);
        return Double.parseDouble(stringValue);
    }

    private Date getElementDateValue(Element parent, String childName){
        String stringValue = getElementStringValue(parent, childName);
        final String dateFormat = "dd.MM.yyyy HH:mm";
        DateFormat format = new SimpleDateFormat(dateFormat, Locale.GERMAN);
        Date date = format.parse(stringValue, new ParsePosition(4));
        return date == null ? new Date() : date;
    }

    private URI getElementUriValue(Element parent, String childName){
        String stringValue = getElementStringValue(parent, childName);
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
