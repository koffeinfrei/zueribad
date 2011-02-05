package org.koffeinfrei.zueribad.ui;

import android.content.Context;
import org.koffeinfrei.zueribad.models.Bath;

import java.util.ArrayList;

public class FavoritesListAdapter extends ListAdapter {

	public FavoritesListAdapter(Context context) {
		super(context);
	}

	@Override
	protected ArrayList<Bath> getList() {
		return new ArrayList<Bath>(bathRepository.getFavorites().values());
	}

}
