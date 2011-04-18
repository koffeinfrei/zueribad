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

import android.graphics.Color;
import android.graphics.Paint;
import android.widget.TextView;
import org.koffeinfrei.zueribad.R;
import org.koffeinfrei.zueribad.config.Constants;
import org.koffeinfrei.zueribad.utils.DateCalculator;

import java.util.Date;

public class ViewModifier {

    /**
     * If the modified date is too old, the temperature label is
     * marked accordingly.
     */
    public static void temperature(TextView view, Date modifiedDate){
        if (DateCalculator.daysAgo(modifiedDate) >= Constants.EXPIRED_MODIFIED_DATE_IN_DAYS){
            view.setPaintFlags(view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            // TODO find a way to reference from colors.xml, or put in Constants.java
            view.setTextColor(Color.parseColor("#99c4c4c4"));
        }
    }

    public static void lastModified(TextView view, Date modifiedDate){
        if (DateCalculator.daysAgo(modifiedDate) >= Constants.EXPIRED_MODIFIED_DATE_IN_DAYS){
            view.setText(view.getText() + "\n" + view.getContext().getString(R.string.info_data_outofdate));
        }
    }
}
