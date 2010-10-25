package org.koffeinfrei.zueribad.overview;

import java.util.Hashtable;

import org.koffeinfrei.zueribad.models.Bath;
import org.koffeinfrei.zueribad.models.BathRepository;

import android.widget.Filter;

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
				if(bath.getName().toUpperCase().contains(uppercaseConstraint) || 
                		bath.getType().toUpperCase().contains(uppercaseConstraint)){
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
