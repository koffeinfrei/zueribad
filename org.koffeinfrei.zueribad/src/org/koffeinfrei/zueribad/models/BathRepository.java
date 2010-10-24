package org.koffeinfrei.zueribad.models;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.koffeinfrei.zueribad.UserSettings;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BathRepository {
	
	private ArrayList<Bath> all;
	private ArrayList<Bath> filtered;
	private ArrayList<Bath> favorites; // TODO: maybe just store ids instead of whole bath objects
	
	private static BathRepository instance = new BathRepository(); 
	
	private BathRepository(){
	}
	
	public void init(Context context) throws IOException, ClassNotFoundException {
		all = new ArrayList<Bath>();
		
		Bath bath = new Bath(1);
		bath.setName("Ein warmes Bad");
		bath.setType("freibad");
		bath.setTemperature(23.0);
		all.add(bath);
		
		bath = new Bath(2);
		bath.setName("Ein saukaltes Flussbad");
		bath.setType("freibad");
		bath.setTemperature(16.0);
		all.add(bath);
		
		bath = new Bath(3);
		bath.setName("Ein mittelmässiges Hallenbad");
		bath.setType("hallenbad");
		bath.setTemperature(21.5);
		all.add(bath);
		
		favorites = UserSettings.loadFavorites(context);
		System.out.println(">>favs: "+favorites);
		if (favorites == null) {
			favorites = new ArrayList<Bath>();
		}
	}
	
	public static BathRepository getInstance(){
		return instance;
	}
	
	public ArrayList<Bath> getAll(){
		return all;
	}
	
	public ArrayList<Bath> getFiltered(){
		if (filtered == null){
			return all;
		}
		return filtered;
	}
	
	public void setFiltered(ArrayList<Bath> baths){
		filtered = baths;
	}
	
	public Bath get(int id){
		Bath bath = all.get(id - 1); //TODO stable enough with id-1?
		
		bath.setPicture(getPicture());
		
		return bath;
	}
	
	public void addToFavorites(Context context, int id) throws IOException {
		favorites.add(all.get(id - 1));
		UserSettings.save(context, UserSettings.KEY_FAVORITES, favorites);
	}
	
	public void removeFromFavorites(Context context, int id) throws IOException {
		favorites.remove(id - 1);
		UserSettings.save(context, UserSettings.KEY_FAVORITES, favorites);
	}
	
	public ArrayList<Bath> getFavorites(){
		return favorites;
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
