package org.koffeinfrei.zueribad.ui;

import android.content.Context;
import org.koffeinfrei.zueribad.models.Bath;

import java.util.ArrayList;
import java.util.Hashtable;

public class FavoritesListAdapter extends ListAdapter {

	public FavoritesListAdapter(Context context) {
		super(context);
	}

	@Override
	protected ArrayList<Bath> getList() {
        Hashtable<Integer,Bath> favorites = bathRepository.getFavorites();
        if (favorites != null){
            return new ArrayList<Bath>(favorites.values());
        }
        return new ArrayList<Bath>();
	}

}
