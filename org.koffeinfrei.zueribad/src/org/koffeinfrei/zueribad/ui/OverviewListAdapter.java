package org.koffeinfrei.zueribad.ui;

import android.content.Context;
import android.widget.Filter;
import org.koffeinfrei.zueribad.models.Bath;

import java.util.ArrayList;

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
