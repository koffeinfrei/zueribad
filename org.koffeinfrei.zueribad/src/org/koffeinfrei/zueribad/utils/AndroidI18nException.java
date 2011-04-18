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

/**
 * This class is the common used exception class that holds an id
 * to an android string resource (-> i18n error message)
 *
 * @author alexis.reigel
 *
 */
public class AndroidI18nException extends Exception {
    private int resourceStringId;

    public AndroidI18nException(int resourceStringId) {
        this(resourceStringId, new Exception());
    }

    public AndroidI18nException(int resourceStringId, Throwable throwable) {
        super(throwable);
        this.resourceStringId = resourceStringId;
    }

    public int getResourceStringId() {
        return resourceStringId;
    }
}
