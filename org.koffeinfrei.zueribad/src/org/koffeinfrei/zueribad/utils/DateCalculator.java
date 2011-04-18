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

import java.util.Calendar;
import java.util.Date;

public class DateCalculator {
    public static final long MILLISECONDS_PER_MINUTE = 60 * 1000;
    public static final long MILLISECONDS_PER_HOUR   = 60 * MILLISECONDS_PER_MINUTE;
    public static final long MILLISECONDS_PER_DAY = 24 * MILLISECONDS_PER_HOUR;

    public static long daysAgo(Date date){
        return diffInDays(date, new Date());
    }

    public static long diffInDays(Date start, Date end) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        long endTicks   =  endCalendar.getTimeInMillis() + endCalendar.getTimeZone().getOffset(endCalendar.getTimeInMillis());
        long startTicks = startCalendar.getTimeInMillis() + startCalendar.getTimeZone().getOffset(startCalendar.getTimeInMillis());
        return (endTicks - startTicks) / MILLISECONDS_PER_DAY;
    }
}
