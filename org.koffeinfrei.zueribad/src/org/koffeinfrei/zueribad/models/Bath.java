package org.koffeinfrei.zueribad.models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bath implements Serializable{
	
	private static final long serialVersionUID = 4857741782219006158L;
	
	private int id;
	private String name;
	private Double temperature;
    private Date modified;
    private String status;
    private String url;
	
	public Bath(int id){
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getTemperature() {
		return temperature;
	}
	
	public String getFormattedTemperature(){
		return temperature + " °C";
	}

    public Date getModified() {
        return modified;
    }

    public String getFormattedModified(){
        DateFormat format = new SimpleDateFormat("E dd.MM.yyyy HH:mm");
        return format.format(modified);
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
