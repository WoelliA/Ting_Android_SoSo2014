package de.ur.mi.android.ting.model;

import de.ur.mi.android.ting.utilities.LoadedImageData;

public class PinData {
	private String title;
	private String description;
	private LoadedImageData imageData;
	private String linkUrl;

	public PinData(String title, String description, LoadedImageData imageData, String linkUrl) {
		this.title = title;
		this.description = description;
		this.imageData = imageData;
		this.linkUrl = linkUrl;
	}

	public String getTitle() {
		return this.title;
	}

	public String getLinkUrl() {
		return this.linkUrl;
	}

	public void setLinkUrl(String url) {
		this.linkUrl = url;
	}

	public String getDescription() {
		return this.description;
	}

	public LoadedImageData getImageData() {
		return this.imageData;
	}

}
