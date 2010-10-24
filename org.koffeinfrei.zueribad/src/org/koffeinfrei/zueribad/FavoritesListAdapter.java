package org.koffeinfrei.zueribad;

import java.util.ArrayList;

import org.koffeinfrei.zueribad.models.Bath;

import android.content.Context;

public class FavoritesListAdapter extends ListAdapter {

	public FavoritesListAdapter(Context context) {
		super(context);
	}

	@Override
	protected ArrayList<Bath> getList() {
		return bathRepository.getFavorites();
	}

}
