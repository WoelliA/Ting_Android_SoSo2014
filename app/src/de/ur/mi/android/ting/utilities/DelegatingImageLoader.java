package de.ur.mi.android.ting.utilities;

import javax.inject.Singleton;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

@Singleton
public class DelegatingImageLoader implements IImageLoader {
	private ImageLoader loader;

	public DelegatingImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).build();
		this.loader = ImageLoader.getInstance();
		loader.init(config);
	}

	@Override
	public void loadImage(String uri, ImageView imageView) {
		DisplayImageOptions options = this.buildOptions().build();
		this.loader.displayImage(uri, imageView, options);
	}

	private Builder buildOptions() {
		return new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED);
	}

	@Override
	public void loadImage(String imageUri, ImageView picture,
			ViewSwitcher switcher, View loadingView) {

		ImageLoadRequest request = new ImageLoadRequest(imageUri, picture,
				switcher, loadingView);
		DisplayImageOptions options = this.buildOptions().build();
		request.execute(loader, options);
	}
}
