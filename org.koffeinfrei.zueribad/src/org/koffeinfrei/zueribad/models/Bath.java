package org.koffeinfrei.zueribad.models;

import java.io.Serializable;

import android.graphics.Bitmap;

public class Bath implements Serializable{
	
	private static final long serialVersionUID = 4857741782219006158L;
	
	private int id;
	private String Name;
	private String Type;
	private Double Temperature;
	private Bitmap Picture;
	
	public Bath(int id){
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setName(String name) {
		Name = name;
	}

	public String getName() {
		return Name;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getType() {
		return Type;
	}

	public void setTemperature(Double temperature) {
		Temperature = temperature;
	}

	public Double getTemperature() {
		return Temperature;
	}
	
	public String getFormattedTemperature(){
		return Temperature + " °C";
	}

	public void setPicture(Bitmap picture) {
		Picture = picture;
	}

	public Bitmap getPicture() {
		return Picture;
	}
}
