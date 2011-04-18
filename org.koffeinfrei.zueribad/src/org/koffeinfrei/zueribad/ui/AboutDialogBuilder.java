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

package org.koffeinfrei.zueribad.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.widget.TextView;
import org.koffeinfrei.zueribad.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AboutDialogBuilder {
    public static AlertDialog create( Context context ) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            // keep on
        }
        String versionInfo = packageInfo != null ? packageInfo.versionName : "";
        String aboutTitle = String.format("%s %s", context.getString(R.string.title_about), context.getString(R.string.app_name));

        String versionString = String.format("%s: %s", context.getString(R.string.title_version), versionInfo);

        String aboutText = context.getString(R.string.text_about);

        Linkify.TransformFilter transformFilter = new Linkify.TransformFilter() {
            public final String transformUrl(final Matcher match, String url) {
                return ""; 
            }
        };

        final TextView message = new TextView(context);
        final SpannableString s = new SpannableString(aboutText);
        message.setPadding(5, 5, 5, 5);
        message.setText(versionString + "\n\n" + s);
        Pattern kPattern = Pattern.compile("koffeinfrei");
        Linkify.addLinks(message, kPattern,  "http://koffeinfrei.org", null, transformFilter);
        Pattern sPattern = Pattern.compile("Schul- und Sportdepartement der Stadt Zürich");
        Linkify.addLinks(message, sPattern,  "http://www.stadt-zuerich.ch/ssd/de/index/sport/schwimmen.html", null, transformFilter);

        return new AlertDialog.Builder(context).setTitle(aboutTitle).setCancelable(true).setIcon(R.drawable.ic_icon).setPositiveButton(
             context.getString(android.R.string.ok), null).setView(message).create();
    }
}
