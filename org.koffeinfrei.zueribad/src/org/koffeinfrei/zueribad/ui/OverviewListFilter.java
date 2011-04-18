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

import android.widget.Filter;
import org.koffeinfrei.zueribad.models.Bath;
import org.koffeinfrei.zueribad.models.BathRepository;

import java.util.Hashtable;

public class OverviewListFilter extends Filter {

	BathRepository bathRepository;
	
	public OverviewListFilter(BathRepository bathRepository) {
		this.bathRepository = bathRepository;
	}

	@Override
	protected FilterResults performFiltering(CharSequence constraint) {
		// NOTE: this method is *always* called from a background thread, and
        // not the UI thread. 

        FilterResults results = new FilterResults();
        
        Hashtable<Integer,Bath> filteredBaths = new Hashtable<Integer,Bath>();
        Hashtable<Integer,Bath> allBaths = bathRepository.getAll();
        
        if (constraint != null && constraint.length() > 0) {
        	String uppercaseConstraint = constraint.toString().toUpperCase();
			for (Bath bath : allBaths.values()) {
				if(bath.getName().toUpperCase().contains(uppercaseConstraint)/* ||
                		bath.getType().toUpperCase().contains(uppercaseConstraint)*/){
					filteredBaths.put(bath.getId(), bath);  
                }
			}
            results.values = filteredBaths;
            results.count = filteredBaths.size();                   
        }
        else{
            synchronized (allBaths){
                results.values = allBaths;
                results.count = allBaths.size();
            }
        }

        return results;
	}

	@Override
	protected void publishResults(CharSequence constraint, FilterResults results) {
		// NOTE: this method is *always* called from the UI thread.
	    @SuppressWarnings("unchecked")
		Hashtable<Integer,Bath> values = (Hashtable<Integer,Bath>)results.values;
		bathRepository.setFiltered(values);
	}

}
