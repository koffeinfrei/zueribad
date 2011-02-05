package org.koffeinfrei.zueribad.models;

import android.content.Context;
import org.koffeinfrei.zueribad.config.UserSettings;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Hashtable;

public class BathRepository {
	
	private Hashtable<Integer, Bath> all;
	private Hashtable<Integer, Bath> filtered;
	private Hashtable<Integer, Bath> favorites; // TODO: maybe just store ids instead of whole bath objects
    private int current;
	
	private static BathRepository instance = new BathRepository(); 
	
	private BathRepository(){
        current = -1;
	}
	
	public void init(Context context) throws IOException, ClassNotFoundException, URISyntaxException, SAXException, ParserConfigurationException {

        BathService service = new BathService("http://www.stadt-zuerich.ch/stzh/bathdatadownload"); // TODO -> settings
        // http://192.168.1.23/stzh_bath_data.xml

        all = service.load();

		favorites = UserSettings.loadFavorites(context);
		
		if (favorites == null) {
			favorites = new Hashtable<Integer, Bath>();
		}
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
	
	public void addToFavorites(Context context, int id) throws IOException {
		favorites.put(id, all.get(id));
		UserSettings.save(context, UserSettings.KEY_FAVORITES, favorites);
	}
	
	public void removeFromFavorites(Context context, int id) throws IOException {
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
}
