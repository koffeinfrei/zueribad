package org.koffeinfrei.zueribad.models;

import java.util.ArrayList;

import org.koffeinfrei.zueribad.models.entities.Bath;

public class BathRepository {
	
	ArrayList<Bath> all;
	ArrayList<Bath> filtered;
	
	public BathRepository(){
		all = new ArrayList<Bath>();
		
		Bath bath = new Bath();
		bath.setName("asdf");
		bath.setType("freibad");
		all.add(bath);
		
		bath = new Bath();
		bath.setName("qwer");
		bath.setType("hallenbad");
		all.add(bath);
		
		bath = new Bath();
		bath.setName("asdfasdf");
		bath.setType("hallenbad");
		all.add(bath);
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
}
