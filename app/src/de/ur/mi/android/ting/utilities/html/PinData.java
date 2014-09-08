package de.ur.mi.android.ting.utilities.html;

import java.util.List;

import de.ur.mi.android.ting.utilities.LoadedImageData;

public class PinData {
	private String description;
	private String title;
	private List<LoadedImageData> imageData;
	
	public PinData(String title, String description, List<LoadedImageData> imageData) {
		this.title = title;
		this.description = description;
		this.imageData = imageData;
	}

	public List<LoadedImageData> getImageData(){
		return this.imageData;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getDescription(){
		return this.description;
	}
}
