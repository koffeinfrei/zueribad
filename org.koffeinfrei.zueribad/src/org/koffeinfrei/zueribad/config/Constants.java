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
            : "https://www.stadt-zuerich.ch/stzh/bathdatadownload";

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

    public static final String DATE_FORMAT_CUSTOM = "dd.MM.yyyy HH:mm";
    public static final String DATE_FORMAT_DATEONLY = "yyyy-MM-dd";
}
