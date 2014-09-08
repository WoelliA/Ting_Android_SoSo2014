package de.ur.mi.android.ting.model;

import de.ur.mi.android.ting.utilities.LoadedImageData;

public class PinData {
	private String title;
	private String description;
	private LoadedImageData imageData;
	
	public PinData(String title, String description, LoadedImageData imageData) {
		this.title = title;
		this.description = description;
		this.imageData = imageData;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public LoadedImageData getImageData(){
		return this.imageData;
	}

}
