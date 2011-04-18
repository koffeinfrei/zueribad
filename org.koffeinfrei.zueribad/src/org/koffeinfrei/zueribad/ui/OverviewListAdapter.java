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
import android.widget.Filter;
import org.koffeinfrei.zueribad.models.Bath;

import java.util.ArrayList;
import java.util.Hashtable;

public class OverviewListAdapter extends ListAdapter {
	
	public OverviewListAdapter(Context context) {
		super(context);
	}

	public Filter getFilter() {
		return new OverviewListFilter(bathRepository);
	}

	@Override
	protected ArrayList<Bath> getList() {
        Hashtable<Integer,Bath> list = bathRepository.getFiltered();
        return new ArrayList<Bath>(list != null ? list.values() : new ArrayList<Bath>());
	}
	
}
