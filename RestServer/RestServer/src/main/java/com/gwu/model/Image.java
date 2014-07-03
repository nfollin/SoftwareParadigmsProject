package com.gwu.model;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.*;
public class Image implements Serializable{
	private static final long serialVersionUID = 1L;
    private float lat=0;
    private float longitude=0;
    private String base64Encoding=null;
    public Image(){
    	
    }
    public Image(float lat, float longitude, String encodedValue){
    	this.lat=lat;
    	this.longitude=longitude;
    	this.base64Encoding=encodedValue;
    }
	public float getLat() {
		return lat;
	}
	
	public void setLat(float lat) {
		this.lat = lat;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public void setBase64Encoding(String base64Encoding) {
		this.base64Encoding = base64Encoding;
	}
	public float getLongitude() {
		return longitude;
	}
	public String getBase64Encoding() {
		return base64Encoding;
	}
	@Override
	public String toString(){
		JsonObject ob = new JsonObject();
		ob.addProperty("lat", this.getLat());
		ob.addProperty("longitude", this.getLongitude());
		ob.addProperty("base64Image", this.getBase64Encoding());
		return ob.toString();
	}

}
