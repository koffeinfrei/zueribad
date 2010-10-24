package org.koffeinfrei.zueribad;

import java.util.ArrayList;

import org.koffeinfrei.zueribad.models.entities.Bath;

import android.content.Context;
import android.widget.Filter;

public class OverviewListAdapter extends ListAdapter {
	
	public OverviewListAdapter(Context context) {
		super(context);
	}

	public Filter getFilter() {
		return new OverviewListFilter(bathRepository);
	}

	@Override
	protected ArrayList<Bath> getList() {
		return bathRepository.getFiltered();
	}
	
}
