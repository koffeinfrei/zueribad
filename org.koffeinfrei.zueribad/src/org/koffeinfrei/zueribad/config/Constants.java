package org.koffeinfrei.zueribad.config;

public class Constants {
    public static final boolean IS_EMULATOR = android.provider.Settings.Secure.ANDROID_ID == null;

    public static final String SERVICE_URL = IS_EMULATOR
            ? "http://192.168.1.24/stzh_bath_data.xml"
            : "http://www.stadt-zuerich.ch/stzh/bathdatadownload";

    public static final int PROGRESS_DIALOG = 1;
    public static final int ERROR_DIALOG = 2;
    
    public static final int TAB_OVERVIEW_INDEX = 0;
    public static final int TAB_OVERVIEW_MAP_INDEX = 1;
    public static final int TAB_FAVORITES_INDEX = 2;
    public static final int TAB_DETAILS_INDEX = 3;

    public static final String STATIC_DATA =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "<bathinfos>\n" +
        "    <baths>\n" +
        "        <bath>\n" +
        "            <title>Flussbad Au-Höngg</title>\n" +
        "            <geoPointX>47.39935</geoPointX>\n" +
        "            <geoPointY>8.48915</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Flussbad Oberer Letten</title>\n" +
        "            <geoPointX>47.39935</geoPointX>\n" +
        "            <geoPointY>8.48915</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Flussbad Unterer Letten</title>\n" +
        "            <geoPointX>47.39935</geoPointX>\n" +
        "            <geoPointY>8.48915</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Frauenbad Stadthausquai</title>\n" +
        "            <geoPointX>47.39935</geoPointX>\n" +
        "            <geoPointY>8.48915</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Freibad Allenmoos</title>\n" +
        "            <geoPointX>47.39935</geoPointX>\n" +
        "            <geoPointY>8.48915</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Freibad Auhof</title>\n" +
        "            <geoPointX>47.39935</geoPointX>\n" +
        "            <geoPointY>8.48915</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Freibad Heuried</title>\n" +
        "            <geoPointX>47.39935</geoPointX>\n" +
        "            <geoPointY>8.48915</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Freibad Letzigraben</title>\n" +
        "            <geoPointX>47.39935</geoPointX>\n" +
        "            <geoPointY>8.48915</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Freibad Seebach</title>\n" +
        "            <geoPointX>47.39935</geoPointX>\n" +
        "            <geoPointY>8.48915</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Freibad Zwischen den Hölzern</title>\n" +
        "            <geoPointX>47.39935</geoPointX>\n" +
        "            <geoPointY>8.48915</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Seebad Katzensee</title>\n" +
        "            <geoPointX>47.39935</geoPointX>\n" +
        "            <geoPointY>8.48915</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Seebad Utoquai</title>\n" +
        "            <geoPointX>47.39935</geoPointX>\n" +
        "            <geoPointY>8.48915</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Strandbad Mythenquai</title>\n" +
        "            <geoPointX>47.39935</geoPointX>\n" +
        "            <geoPointY>8.48915</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Strandbad Tiefenbrunnen</title>\n" +
        "            <geoPointX>47.39935</geoPointX>\n" +
        "            <geoPointY>8.48915</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Strandbad Wollishofen</title>\n" +
        "            <geoPointX>47.39935</geoPointX>\n" +
        "            <geoPointY>8.48915</geoPointY>\n" +
        "        </bath>\n" +
        "    </baths>\n" +
        "</bathinfos>";
}
