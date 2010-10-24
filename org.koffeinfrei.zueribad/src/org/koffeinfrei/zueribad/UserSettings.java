package org.koffeinfrei.zueribad;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import org.koffeinfrei.zueribad.models.Bath;
import org.koffeinfrei.zueribad.utils.StringSerializer;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSettings {
	public static final String KEY_FAVORITES = "favorites";
	
	//TODO serialize baths
	public static void save(Context context, String key, String value) {
      SharedPreferences settings = context.getSharedPreferences(UserSettings.class.getName(), 0);
      SharedPreferences.Editor editor = settings.edit();
      editor.putString(key, value);
      editor.commit();
	}
	
	public static void save(Context context, String key, ArrayList<Bath> values) throws IOException {
		String value = StringSerializer.serialize(values);
		save(context, key, value);
	}
	
	public static Serializable load(Context context, String key) throws IOException, ClassNotFoundException {
		SharedPreferences settings = context.getSharedPreferences(UserSettings.class.getName(), 0);
		String value = settings.getString(key, null);
		return StringSerializer.deserialize(value);
	}
	
	public static void reset(Context context) {
		SharedPreferences settings = context.getSharedPreferences(UserSettings.class.getName(), 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.clear();
		editor.commit();
	}
	
	public static ArrayList<Bath> loadFavorites(Context context) throws IOException, ClassNotFoundException{
		return (ArrayList<Bath>)load(context, KEY_FAVORITES);
	}
}

