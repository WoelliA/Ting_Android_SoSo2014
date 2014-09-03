package de.ur.mi.android.ting.app.viewResolvers;

import javax.inject.Inject;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.utilities.IImageLoader;
import de.ur.mi.android.ting.utilities.view.ViewResolver;
import de.ur.mi.android.ting.views.Loading;

public class PinListViewResolver extends ViewResolver<Pin> {

	@Inject
	public IImageLoader imageLoader;

	public PinListViewResolver(Context context) {
		super(R.layout.pin_layout, context);
	}

	@Override
	protected void decorateView(View view, Pin pin, ViewGroup parent) {

		TextView headline = (TextView) this.findViewById(view,
				R.id.pin_headline);
		TextView content = (TextView) this.findViewById(view, R.id.pin_content);
		ImageView picture = (ImageView) this.findViewById(view,
				R.id.pin_picture);

		headline.setText(pin.getTitle());
		content.setText(pin.getDescription());

		ViewSwitcher switcher = (ViewSwitcher) this.findViewById(view,
				R.id.pin_loading_switcher);

		String imageUri = pin.getImageUri();
		if (switcher.getChildCount() < 2 && !this.isSameImage(picture, imageUri)) {
			picture.setTag(imageUri);
			this.imageLoader.loadImage(imageUri, picture, switcher,
					Loading.getView(this.getContext(), ""));
		} else {
		}

	}

	private boolean isSameImage(View view, String imageUri) {
		Object tag = view.getTag();
		boolean isSame = tag != null && tag.equals(imageUri);
		if (isSame) {
			Log.i("image", "is same");
		}
		return isSame;
	}

}
