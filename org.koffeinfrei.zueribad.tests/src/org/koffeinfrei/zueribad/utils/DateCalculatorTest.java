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

import junit.framework.TestCase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCalculatorTest extends TestCase {
    public void test_diffInDays_diffIs47Hours_Returns1() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
        Date start = dateFormat.parse("02.02.2011 13:00");
        Date end = dateFormat.parse("04.02.2011 12:59");
        long diff = DateCalculator.diffInDays(start, end);
        assertEquals(1, diff);
    }

    public void test_diffInDays_diffIs48Hours_Returns1() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
        Date start = dateFormat.parse("02.02.2011 13:00");
        Date end = dateFormat.parse("04.02.2011 13:00");
        long diff = DateCalculator.diffInDays(start, end);
        assertEquals(2, diff);
    }

    public void test_diffInDays_sameDates_Returns0() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
        Date start = dateFormat.parse("02.02.2011 13:00");
        Date end = dateFormat.parse("02.02.2011 13:00");
        long diff = DateCalculator.diffInDays(start, end);
        assertEquals(0, diff);
    }
}
