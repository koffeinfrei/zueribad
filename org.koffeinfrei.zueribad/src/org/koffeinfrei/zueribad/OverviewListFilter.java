package org.koffeinfrei.zueribad;

import java.util.ArrayList;

import org.koffeinfrei.zueribad.models.BathRepository;
import org.koffeinfrei.zueribad.models.entities.Bath;

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
        
        ArrayList<Bath> filteredBaths = new ArrayList<Bath>();
        ArrayList<Bath> allBaths = bathRepository.getAll();
        
        if (constraint != null && constraint.length() > 0) {
        	String uppercaseConstraint = constraint.toString().toUpperCase();
			for (int index = 0; index < allBaths.size(); index++) {
                Bath bath = allBaths.get(index);
                if(bath.getName().toUpperCase().contains(uppercaseConstraint) || 
                		bath.getType().toUpperCase().contains(uppercaseConstraint)){
                  filteredBaths.add(bath);  
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
		ArrayList<Bath> values = (ArrayList<Bath>)results.values;
		bathRepository.setFiltered(values);
	}

}
