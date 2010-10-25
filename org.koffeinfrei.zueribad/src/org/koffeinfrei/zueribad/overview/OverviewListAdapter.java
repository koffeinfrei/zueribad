package org.koffeinfrei.zueribad.overview;

import java.util.ArrayList;
import org.koffeinfrei.zueribad.ListAdapter;
import org.koffeinfrei.zueribad.models.Bath;

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
		return new ArrayList<Bath>(bathRepository.getFiltered().values());
	}
	
}
