package de.ur.mi.android.ting.utilities;

import android.graphics.Bitmap;

public class LoadedImageData {

	private String imageUrl;
	private Bitmap bitmap;
	
	public LoadedImageData(String imageUrl, Bitmap bitmap
			) {
				this.imageUrl = imageUrl;
				this.bitmap = bitmap;
	}

	public String getimageUrl(){
		return this.imageUrl;
	}
	
	public Bitmap getBitmap(){
		return this.bitmap;
	}

	public void setImageUrl(String url) {
		this.imageUrl = url;
		
	}
}
