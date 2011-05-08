/*
 *  Koffeinfrei Zueribad
 *  Copyright (C) 2011  Alexis Reigel
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.koffeinfrei.zueribad.models;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AndroidException;
import com.google.android.maps.GeoPoint;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bath implements Serializable{
	
	private static final long serialVersionUID = 4857741782219006158L;
	
	private transient int id;
	private String name;
	private transient Double temperature;
    private transient Date modified;
    private transient String status;
    private transient String url;
    private transient GeoPoint geoPoint;
    private transient String address;
    private transient String address2;
    private transient String route;
    private transient Date seasonStart;
    private transient Date seasonEnd;
    private transient String openingHoursInfo;

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

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }

    public Date getSeasonStart() {
        return seasonStart;
    }

    public void setSeasonStart(Date seasonStart) {
        this.seasonStart = seasonStart;
    }

    public Date getSeasonEnd() {
        return seasonEnd;
    }

    public void setSeasonEnd(Date seasonEnd) {
        this.seasonEnd = seasonEnd;
    }

    public void setOpeningHoursInfo(String openingHoursInfo) {
        this.openingHoursInfo = openingHoursInfo;
    }

    public String getOpeningHoursInfo() {
        return openingHoursInfo;
    }

    public Drawable getBathImage(Context context) throws AndroidException {
        try {
            InputStream inputStream = context.getAssets().open("img_baths/" + getAssetName() + ".jpg");
            return Drawable.createFromStream(inputStream, "src");
        } catch (IOException e) {
            throw new AndroidException(e);
        }
    }

    private String getAssetName(){
        // omit bath type
        int firstWhitespacePosition = name.indexOf(" ");
        String assetName = name.substring(firstWhitespacePosition + 1);

        assetName = assetName.toLowerCase();

        assetName = assetName.replaceAll("ä", "ae").replaceAll("ö", "oe").replaceAll("ü", "ue");

        assetName = assetName.replaceAll(" ", "_");

        return assetName;
    }
}
