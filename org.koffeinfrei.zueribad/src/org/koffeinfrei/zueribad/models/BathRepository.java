package org.koffeinfrei.zueribad.models;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

import org.koffeinfrei.zueribad.UserSettings;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BathRepository {
	
	private Hashtable<Integer, Bath> all;
	private Hashtable<Integer, Bath> filtered;
	private Hashtable<Integer, Bath> favorites; // TODO: maybe just store ids instead of whole bath objects
	
	private static BathRepository instance = new BathRepository(); 
	
	private BathRepository(){
	}
	
	public void init(Context context) throws IOException, ClassNotFoundException {
		all = new Hashtable<Integer, Bath>();
		
		Bath bath = new Bath(1);
		bath.setName("Ein warmes Bad");
		bath.setType("freibad");
		bath.setTemperature(23.0);
		all.put(1, bath);
		
		bath = new Bath(2);
		bath.setName("Ein saukaltes Flussbad");
		bath.setType("freibad");
		bath.setTemperature(16.0);
		all.put(2, bath);
		
		bath = new Bath(3);
		bath.setName("Ein mittelmässiges Hallenbad");
		bath.setType("hallenbad");
		bath.setTemperature(21.5);
		all.put(3, bath);
		
		favorites = UserSettings.loadFavorites(context);
		//System.out.println(">>favs: "+favorites);
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
		
		bath.setPicture(getPicture());
		
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
	
	private Bitmap getPicture() {
		try {
			Bitmap bitmap = BitmapFactory
					.decodeStream((InputStream) new URL(
							"http://www.koffeinfrei.org/uploads/images/base/hdrpic.jpg")
							.getContent());
			
			return bitmap;
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return null; // TODO return placeholder image
	}
}
