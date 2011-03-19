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
