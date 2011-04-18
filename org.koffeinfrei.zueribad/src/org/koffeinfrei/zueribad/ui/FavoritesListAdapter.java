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

import android.content.Context;
import org.koffeinfrei.zueribad.models.Bath;

import java.util.ArrayList;
import java.util.Hashtable;

public class FavoritesListAdapter extends ListAdapter {

	public FavoritesListAdapter(Context context) {
		super(context);
	}

	@Override
	protected ArrayList<Bath> getList() {
        Hashtable<Integer,Bath> favorites = bathRepository.getFavorites();
        if (favorites != null){
            return new ArrayList<Bath>(favorites.values());
        }
        return new ArrayList<Bath>();
	}

}
