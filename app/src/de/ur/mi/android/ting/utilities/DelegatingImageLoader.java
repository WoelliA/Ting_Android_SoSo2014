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
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

@Singleton
public class DelegatingImageLoader implements IImageLoader {
	private ImageLoader loader;

	public DelegatingImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).build();
		this.loader = ImageLoader.getInstance();
		this.loader.init(config);
	}

	@Override
	public void loadImage(String uri, ImageView imageView) {
		if (this.isNoImageUri(uri)) {
			return;
		}
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
		if (this.isNoImageUri(imageUri)) {
			return;
		}
		ImageLoadRequest request = new ImageLoadRequest(imageUri, picture,
				switcher, loadingView);
		DisplayImageOptions options = this.buildOptions().build();
		request.execute(this.loader, options);
	}

	private boolean isNoImageUri(String imageUri) {
		return imageUri == null || imageUri.trim().length() == 0;
	}

	@Override
	public void loadImage(String imageUri,
			final IDoneCallback<Bitmap> doneCallback) {
		if (this.isNoImageUri(imageUri)) {
			return;
		}
		DisplayImageOptions options = this.buildOptions()
				.imageScaleType(ImageScaleType.NONE).build();
		this.loader.loadImage(imageUri, options,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						doneCallback.done(loadedImage);
						super.onLoadingComplete(imageUri, view, loadedImage);
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						doneCallback.done(null);
						super.onLoadingFailed(imageUri, view, failReason);
					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {
						doneCallback.done(null);
						super.onLoadingCancelled(imageUri, view);
					}

				});

	}
}
