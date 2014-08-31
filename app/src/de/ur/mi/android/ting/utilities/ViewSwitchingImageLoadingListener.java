package de.ur.mi.android.ting.utilities;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ViewSwitcher;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ViewSwitchingImageLoadingListener extends
		SimpleImageLoadingListener {
	private final View loadingView;
	private final ViewSwitcher switcher;

	ViewSwitchingImageLoadingListener(View loadingView,
			ViewSwitcher switcher) {
		this.loadingView = loadingView;
		this.switcher = switcher;
	}

	@Override
	public void onLoadingStarted(String imageUri, View view) {
		super.onLoadingStarted(imageUri, view);
		switcher.addView(loadingView);
		switcher.showNext();
	}

	@Override
	public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		super.onLoadingComplete(imageUri, view, loadedImage);
		this.resetSwitcher();
	}

	@Override
	public void onLoadingFailed(String imageUri, View view,
			FailReason failReason) {
		super.onLoadingFailed(imageUri, view, failReason);
		this.resetSwitcher();
		view.setTag(failReason.getCause().getMessage());
	}

	@Override
	public void onLoadingCancelled(String imageUri, View view) {
		this.resetSwitcher();
		super.onLoadingCancelled(imageUri, view);
	}

	private void resetSwitcher() {
		switcher.showPrevious();
		switcher.removeView(loadingView);
	}

}
