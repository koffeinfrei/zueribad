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
            ? "http://192.168.1.39/stzh_bath_data.xml"
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
        "            <address>Werdinsel 2\n8049 Zürich</address>\n" +
        "            <address2>Telefon 044 341 71 93\nLeitung: Daniel Hof</address2>\n" +
        "            <route>Tram 4 bis «Tüffenwies»\nBus 80/89 bis «Winzerhalde»\nÖffentliche Parkplätze (unter Europabrücke oder Blaue Zone)</route>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Flussbad Oberer Letten</title>\n" +
        "            <geoPointX>47.385687</geoPointX>\n" +
        "            <geoPointY>8.534254</geoPointY>\n" +
        "            <address>Lettensteg 10\n8031 Zürich</address>\n" +
        "            <address2>Telefon 044 362 92 00\nLeitung: Adrian Kehl</address2>\n" +
        "            <route>Tram 4/13, Bus bis «Limmatplatz»\nBus 46 bis «Nordstrasse»\nÖffentliche Parkplätze (Blaue Zone)</route>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Flussbad Unterer Letten</title>\n" +
        "            <geoPointX>47.390076</geoPointX>\n" +
        "            <geoPointY>8.528622</geoPointY>\n" +
        "            <address>Wasserwerkstrasse 141\n8037 Zürich</address>\n" +
        "            <address2>Telefon 044 362 10 80\nLeitung: Heinrich Stadler</address2>\n" +
        "            <route>Tram 4/13 bis «Dammweg»\nBus 46 bis «Nordstrasse»\nS2/S8/S14 bis Bahnhof Wipkingen\nÖffentliche Parkplätze</route>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Frauenbad Stadthausquai</title>\n" +
        "            <geoPointX>47.36846</geoPointX>\n" +
        "            <geoPointY>8.541937</geoPointY>\n" +
        "            <address>Stadthausquai\n8001 Zürich</address>\n" +
        "            <address2>Telefon 044 211 95 92 \nLeitung: Nathalie Schneider</address2>\n" +
        "            <route>Tram 4/15 bis «Helmhaus»\nTram 2/5/6/7/8/9/11/13 bis «Paradeplatz»\nLimmatschiff bis «Bürkliplatz»</route>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Freibad Allenmoos</title>\n" +
        "            <geoPointX>47.40553</geoPointX>\n" +
        "            <geoPointY>8.539007</geoPointY>\n" +
        "            <address>Ringstrasse 79\n8057 Zürich</address>\n" +
        "            <address2>Telefon 044 315 50 00\nLeitung: Reto Heuberger</address2>\n" +
        "            <route>Tram 11 bis «Bad Allenmoos»\nÖffentliche Parkplätze (Blaue Zone)</route>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Freibad Auhof</title>\n" +
        "            <geoPointX>47.408672</geoPointX>\n" +
        "            <geoPointY>8.57131</geoPointY>\n" +
        "            <address>Luegislandstrasse 160\n8051 Zürich</address>\n" +
        "            <address2>Telefon 044 322 21 41 \nLeitung: Lukas Oppliger</address2>\n" +
        "            <route>Tram 7/9 bis «Schwamendingerplatz»\nBus 63/79/94 bis «Aubrücke»\nÖffentliche Parkplätze (kostenpflichtig)</route>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Freibad Heuried</title>\n" +
        "            <geoPointX>47.367737</geoPointX>\n" +
        "            <geoPointY>8.505909</geoPointY>\n" +
        "            <address>Wasserschöpfi 71\n8055 Zürich</address>\n" +
        "            <address2>Telefon 044 455 51 61\nLeitung: Roland Bächler</address2>\n" +
        "            <route>Tram 9/14 bis «Heuried»\nÖffentliche Parkplätze (Tiefgarage oder Blaue Zone), rollstuhlgängig</route>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Freibad Letzigraben</title>\n" +
        "            <geoPointX>47.380135</geoPointX>\n" +
        "            <geoPointY>8.499813</geoPointY>\n" +
        "            <address>Edelweissstrasse 5\n8048 Zürich</address>\n" +
        "            <address2>Telefon 044 492 10 50\nLeitung: vakant</address2>\n" +
        "            <route>Tram 2 bis «Letzigrund» \nTram 3, Bus 72/89 bis «Hubertus»\nÖffentliche Parkplätze (Blaue Zone)</route>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Freibad Seebach</title>\n" +
        "            <geoPointX>47.423773</geoPointX>\n" +
        "            <geoPointY>8.548291</geoPointY>\n" +
        "            <address>Glattalstrasse 41\n8052 Zürich</address>\n" +
        "            <address2>Telefon 044 302 78 33\nLeitung: Martin Hunold</address2>\n" +
        "            <route>Tram 14 bis Endstation Seebach\nÖffentliche Parkplätze an der Endstation (kostenpflichtig)</route>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Freibad Zwischen den Hölzern</title>\n" +
        "            <geoPointX>47.409524</geoPointX>\n" +
        "            <geoPointY>8.473765</geoPointY>\n" +
        "            <address>Zwischen den Hölzern\n8102 Oberengstringen</address>\n" +
        "            <address2>Telefon 044 750 01 77\nLeitung: Doris Hängärtner</address2>\n" +
        "            <route>Tram 13 bis «Frankental», Fussweg 12 Min.\nBus 89 «Riedhofstrasse», Fussweg 7 Min.\nGrosser Parkplatz</route>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Seebad Katzensee</title>\n" +
        "            <geoPointX>47.430845</geoPointX>\n" +
        "            <geoPointY>8.493294</geoPointY>\n" +
        "            <address>Katzenseestrasse\n8046 Zürich</address>\n" +
        "            <address2>Telefon 044 371 08 90\nAnsprechperson: Marc Welti</address2>\n" +
        "            <route>Bus 32 bis Endstation «Holzerhurd»</route>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Seebad Utoquai</title>\n" +
        "            <geoPointX>47.361789</geoPointX>\n" +
        "            <geoPointY>8.547226</geoPointY>\n" +
        "            <address>Utoquai\n8008 Zürich</address>\n" +
        "            <address2>Telefon 044 251 61 51\nLeitung: Roger Herrmann</address2>\n" +
        "            <route>Tram 2/4 bis «Kreuzstrasse»\nSchiff bis «Theater»</route>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Strandbad Mythenquai</title>\n" +
        "            <geoPointX>47.354947</geoPointX>\n" +
        "            <geoPointY>8.534615</geoPointY>\n" +
        "            <address>Mythenquai 95\n8002 Zürich</address>\n" +
        "            <address2>Telefon 044 201 00 00\nLeitung: Franco Seiler</address2>\n" +
        "            <route>Tram 7 bis «Brunaustrasse»\nBus 161/165 bis «Sukkulentensammlung»\nLimmatschiff bis «Seerestaurant»\nÖffentliche Parkplätze (kostenpflichtig)</route>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Strandbad Tiefenbrunnen</title>\n" +
        "            <geoPointX>47.352397</geoPointX>\n" +
        "            <geoPointY>8.557541</geoPointY>\n" +
        "            <address>Bellerivestrasse 200\n8008 Zürich</address>\n" +
        "            <address2>Telefon 044 422 32 00\nLeitung: Jürg Randegger</address2>\n" +
        "            <route>Tram 2/4, Bus 33 bis «Fröhlichstrasse»\nBus 912/916 bis «Chinagarten»\nSchiff bis «Casino Zürichhorn» \nÖffentliche Parkplätze (kostenpflichtig)</route>\n" +
        "        </bath>\n" +
        "        <bath>\n" +
        "            <title>Strandbad Wollishofen</title>\n" +
        "            <geoPointX>47.340952</geoPointX>\n" +
        "            <geoPointY>8.537305</geoPointY>\n" +
        "            <address>Seestrasse 451\n8038 Zürich</address>\n" +
        "            <address2>Telefon 044 482 23 20\nLeitung: Ruth Howald</address2>\n" +
        "            <route>Tram 7 bis «Post Wollishofen»\nBus 161/165 bis «Rote Fabrik»\nSchifffahrt Haltestelle «Bhf. Wollishofen»\nÖffentliche Parkplätze (kostenpflichtig)</route>\n" +
        "        </bath>\n" +
        "    </baths>\n" +
        "</bathinfos>";
}
