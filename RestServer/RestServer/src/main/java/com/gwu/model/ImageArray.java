package com.gwu.model;

import java.io.Serializable;

public class ImageArray implements Serializable {
	private static final long serialVersionUID = 2L;
	private Image [] images;
	

	public Image[] getArray() {
		return images;
	}

	public void setArray(Image[] array) {
		this.images = array;
	}
	
}
