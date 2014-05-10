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

package org.koffeinfrei.zueribad.utils;

import android.content.Context;

import org.koffeinfrei.zueribad.R;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BetterDate {
    public static final long MILLISECONDS_PER_MINUTE = 60 * 1000;
    public static final long MILLISECONDS_PER_HOUR   = 60 * MILLISECONDS_PER_MINUTE;
    public static final long MILLISECONDS_PER_DAY = 24 * MILLISECONDS_PER_HOUR;

    public static final BetterDate MIN_DATE = new BetterDate(new Date(0));

    private Date date;

    public BetterDate(){
        this.date = new Date();
    }

    public BetterDate(Date date) {
        this.date = date;
    }

    public long daysAgo(){
        return diffInDays(new BetterDate());
    }

    public Date getDate(){
        return date;
    }

    public long diffInDays(BetterDate end) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(date);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end.date);
        long endTicks   =  endCalendar.getTimeInMillis() + endCalendar.getTimeZone().getOffset(endCalendar.getTimeInMillis());
        long startTicks = startCalendar.getTimeInMillis() + startCalendar.getTimeZone().getOffset(startCalendar.getTimeInMillis());
        return (endTicks - startTicks) / MILLISECONDS_PER_DAY;
    }

    public static BetterDate parse(String stringValue, String dateFormat){
        return parse(stringValue, dateFormat, 0);
    }

    public static BetterDate parse(String stringValue, String dateFormat, int parsePosition){
        DateFormat format = new SimpleDateFormat(dateFormat, Locale.GERMAN);
        Date date = format.parse(stringValue, new ParsePosition(parsePosition));
        return date == null? null : new BetterDate(date);
    }

    public String prettyFormat(Context context){
        long daysAgo = daysAgo();
        if (daysAgo == 0 || daysAgo == 1){
            String value =
                    (daysAgo == 0
                        ? context.getString(R.string.text_today)
                        : context.getString(R.string.text_yesterday)
                    )
                    + " ";
            DateFormat format = new SimpleDateFormat("HH:mm");
            value += format.format(date);
            return value;
        }
        DateFormat format = new SimpleDateFormat("E dd.MM.yyyy HH:mm");
        return format.format(date);
    }
}
