package org.koffeinfrei.zueribad.config;

import android.content.Context;
import android.content.SharedPreferences;
import org.koffeinfrei.zueribad.R;
import org.koffeinfrei.zueribad.models.Bath;
import org.koffeinfrei.zueribad.utils.AndroidI18nException;
import org.koffeinfrei.zueribad.utils.StringSerializer;

import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;

public class UserSettings {
	public static final String KEY_FAVORITES = "favorites";
	
	//TODO serialize baths
	public static void save(Context context, String key, String value) {
      SharedPreferences settings = context.getSharedPreferences(UserSettings.class.getName(), 0);
      SharedPreferences.Editor editor = settings.edit();
      editor.putString(key, value);
      editor.commit();
	}
	
	public static void save(Context context, String key, Hashtable<Integer, Bath> values) throws AndroidI18nException {
        String value;
        try {
            value = StringSerializer.serialize(values);
        } catch (IOException e) {
            throw new AndroidI18nException(R.string.error_savesettings, e);
        }
        save(context, key, value);
	}
	
	public static Serializable load(Context context, String key) throws AndroidI18nException {
		SharedPreferences settings = context.getSharedPreferences(UserSettings.class.getName(), 0);
		String value = settings.getString(key, null);
        try {
            return StringSerializer.deserialize(value);
        } catch (IOException e) {
            throw new AndroidI18nException(R.string.error_loadsettings, e);
        } catch (ClassNotFoundException e) {
            throw new AndroidI18nException(R.string.error_loadsettings, e);
        }
    }
	
	public static void reset(Context context) {
		SharedPreferences settings = context.getSharedPreferences(UserSettings.class.getName(), 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.clear();
		editor.commit();
	}
	
	public static Hashtable<Integer, Bath> loadFavorites(Context context) throws AndroidI18nException {
		return (Hashtable<Integer, Bath>)load(context, KEY_FAVORITES);
	}
}

