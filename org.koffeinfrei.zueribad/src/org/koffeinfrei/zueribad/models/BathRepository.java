package org.koffeinfrei.zueribad.models;

import android.content.Context;
import android.graphics.drawable.Drawable;
import org.koffeinfrei.zueribad.config.Constants;
import org.koffeinfrei.zueribad.config.UserSettings;
import org.koffeinfrei.zueribad.utils.AndroidI18nException;

import java.util.Hashtable;

public class BathRepository {
	
	private Hashtable<Integer, Bath> all;
	private Hashtable<Integer, Bath> filtered;
	private Hashtable<Integer, Bath> favorites; // TODO: maybe just store ids instead of whole bath objects
    private int current;
    private Drawable uvIndexImage;
	
	private static BathRepository instance = new BathRepository(); 
	
	private BathRepository(){
        current = -1;
	}
	
	public void init(Context context) throws AndroidI18nException {

        BathService service = new BathService(Constants.SERVICE_URL, Constants.STATIC_DATA);

        all = service.load();
        uvIndexImage = service.getUvIndexImage();

		favorites = UserSettings.loadFavorites(context);
		
		if (favorites == null) {
			favorites = new Hashtable<Integer, Bath>();
		}
	}

    public void clear(){
        all = null;
        filtered = null;
    }
	
	public static BathRepository getInstance(){
		return instance;
	}
	
	public Hashtable<Integer,Bath> getAll(){
		return all;
	}
	
	public Hashtable<Integer,Bath> getFiltered(){
		if (filtered == null){
			return all;
		}
		return filtered;
	}
	
	public void setFiltered(Hashtable<Integer,Bath> baths){
		filtered = baths;
	}
	
	public Bath get(int id){
		Bath bath = all.get(id);
		
		return bath;
	}
	
	public void addToFavorites(Context context, int id) throws AndroidI18nException {
		favorites.put(id, all.get(id));
		UserSettings.save(context, UserSettings.KEY_FAVORITES, favorites);
	}
	
	public void removeFromFavorites(Context context, int id) throws AndroidI18nException {
		favorites.remove(id);
		UserSettings.save(context, UserSettings.KEY_FAVORITES, favorites);
	}
	
	public Hashtable<Integer,Bath> getFavorites(){
		return favorites;
	}
	
	public boolean isFavorite(int id) {
		return favorites.containsKey(id);
	}

    public Bath getCurrent() {
        if (current == -1){
            return null;
        }

        return all.get(current);
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public Drawable getUvIndexImage() {
        return uvIndexImage;
    }
}
