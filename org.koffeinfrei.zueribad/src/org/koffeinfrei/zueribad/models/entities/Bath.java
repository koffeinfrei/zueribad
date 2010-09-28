package org.koffeinfrei.zueribad.models.entities;

public class Bath {
	
	private String Name;
	private String Type;
	private Double Temperature;
	
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
}
