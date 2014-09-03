package de.ur.mi.android.ting.utilities;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class ImageLoadRequest {

	private String imageUri;
	private ImageView picture;
	private ViewSwitcher switcher;
	private View loadingView;

	public ImageLoadRequest(String imageUri, ImageView picture,
			ViewSwitcher switcher, View loadingView) {
		this.imageUri = imageUri;
		this.picture = picture;
		this.switcher = switcher;
		this.loadingView = loadingView;
	}

	public void execute(ImageLoader loader, DisplayImageOptions options) {	
		loader.displayImage(this.imageUri, this.picture, options,
				new ViewSwitchingImageLoadingListener(this.loadingView, this.switcher));
	}

}
