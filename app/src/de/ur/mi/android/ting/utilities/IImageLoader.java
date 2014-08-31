package de.ur.mi.android.ting.utilities;

import android.view.View;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public interface IImageLoader {
	public void loadImage(String uri, ImageView picture);

	void loadImage(String imageUri, ImageView picture, ViewSwitcher switcher,
			View loadingView);
}
