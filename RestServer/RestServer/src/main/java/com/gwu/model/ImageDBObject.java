package com.gwu.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.gwu.tools.ImageTools;
 
@Document(collection = "images")
public class ImageDBObject {
	@Id
	private String id;
	int lat;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getLat() {
		return lat;
	}
	public void setLat(float lat) {
		
		this.lat = Math.round(lat);
	}
	public int getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = Math.round(longitude);
	}
	public byte[] getData() {
		return data;
	}
	public String getDataAsString(){
		return ImageTools.arrayToEncoding(data);
	}
	public void setData(String encoding) {
		data = ImageTools.encodingToArray(encoding);
		this.data = data;
	}
	int longitude;
	byte[] data;
	public ImageDBObject(){
		
	}
}
