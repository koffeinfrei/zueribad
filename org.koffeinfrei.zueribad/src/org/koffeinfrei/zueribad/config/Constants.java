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

package org.koffeinfrei.zueribad.config;

import android.os.Build;
import com.google.android.maps.GeoPoint;

public class Constants {
    public static final boolean IS_EMULATOR = Build.PRODUCT.equals("google_sdk");

    public static final String SERVICE_URL = IS_EMULATOR
            ? "http://192.168.1.38/stzh_bath_data.xml"
            : "http://www.stadt-zuerich.ch/stzh/bathdatadownload";

    public static final int PROGRESS_DIALOG = 1;
    public static final int ERROR_DIALOG = 2;

    public static final int TAB_OVERVIEW_INDEX = 0;
    public static final int TAB_OVERVIEW_MAP_INDEX = 1;
    public static final int TAB_FAVORITES_INDEX = 2;
    public static final int TAB_DETAILS_INDEX = 3;

    public static final int EXPIRED_MODIFIED_DATE_IN_DAYS = 1;

    public static final double GEO_POINT_MULTIPLIER = 1000000.0;

    public static final GeoPoint MAP_CENTRE = new GeoPoint(
            (int)(47.381799 * GEO_POINT_MULTIPLIER),
            (int)(8.535328 * GEO_POINT_MULTIPLIER));

    public static final String STATIC_DATA =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "<bathinfos>\n" +
        "    <baths>\n" +
        "        <bath>\n" +
        "            <title>Flussbad Au-Höngg</title>\n" +
        "            <geoPointX>47.399166</geoPointX>\n" +
        "            <geoPointY>8.489263</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Flussbad Oberer Letten</title>\n" +
        "            <geoPointX>47.385687</geoPointX>\n" +
        "            <geoPointY>8.534254</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Flussbad Unterer Letten</title>\n" +
        "            <geoPointX>47.390076</geoPointX>\n" +
        "            <geoPointY>8.528622</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Frauenbad Stadthausquai</title>\n" +
        "            <geoPointX>47.36846</geoPointX>\n" +
        "            <geoPointY>8.541937</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Freibad Allenmoos</title>\n" +
        "            <geoPointX>47.40553</geoPointX>\n" +
        "            <geoPointY>8.539007</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Freibad Auhof</title>\n" +
        "            <geoPointX>47.408672</geoPointX>\n" +
        "            <geoPointY>8.57131</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Freibad Heuried</title>\n" +
        "            <geoPointX>47.367737</geoPointX>\n" +
        "            <geoPointY>8.505909</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Freibad Letzigraben</title>\n" +
        "            <geoPointX>47.380135</geoPointX>\n" +
        "            <geoPointY>8.499813</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Freibad Seebach</title>\n" +
        "            <geoPointX>47.423773</geoPointX>\n" +
        "            <geoPointY>8.548291</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Freibad Zwischen den Hölzern</title>\n" +
        "            <geoPointX>47.409524</geoPointX>\n" +
        "            <geoPointY>8.473765</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Seebad Katzensee</title>\n" +
        "            <geoPointX>47.430845</geoPointX>\n" +
        "            <geoPointY>8.493294</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Seebad Utoquai</title>\n" +
        "            <geoPointX>47.361789</geoPointX>\n" +
        "            <geoPointY>8.547226</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Strandbad Mythenquai</title>\n" +
        "            <geoPointX>47.354947</geoPointX>\n" +
        "            <geoPointY>8.534615</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Strandbad Tiefenbrunnen</title>\n" +
        "            <geoPointX>47.352397</geoPointX>\n" +
        "            <geoPointY>8.557541</geoPointY>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Strandbad Wollishofen</title>\n" +
        "            <geoPointX>47.340952</geoPointX>\n" +
        "            <geoPointY>8.537305</geoPointY>\n" +
        "        </bath>\n" +
        "    </baths>\n" +
        "</bathinfos>";
}
