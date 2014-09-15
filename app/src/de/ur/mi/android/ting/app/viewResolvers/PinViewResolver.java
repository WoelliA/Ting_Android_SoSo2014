package de.ur.mi.android.ting.app.viewResolvers;

import javax.inject.Inject;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ViewSwitcher;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.controllers.PinController;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.utilities.IImageLoader;
import de.ur.mi.android.ting.utilities.view.Loading;
import de.ur.mi.android.ting.utilities.view.ViewResolver;

public class PinViewResolver extends ViewResolver<Pin> {

	@Inject
	public IImageLoader imageLoader;

	@Inject
	public PinController controller;

	public PinViewResolver(Context context) {
		super(R.layout.pin_layout, context);
		this.controller.setup(context);
	}

	@Override
	protected void decorateView(View view, final Pin pin, ViewGroup parent) {

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
		if (switcher.getChildCount() < 2
				&& !this.isSameImage(picture, imageUri)) {
			picture.setTag(imageUri);
			this.imageLoader.loadImage(imageUri, picture, switcher,
					Loading.getView(this.getContext(), ""));
		} else {
		}

		this.setupControls(view, pin);
	}

	private void setupControls(View view, final Pin pin) {
		ViewGroup controls = (ViewGroup) this.findViewById(view,
				R.id.pin_controls);
		if (this.controller.getIsOwned(pin)) {
			controls.setVisibility(View.GONE);
			return;
		}
		controls.setVisibility(View.VISIBLE);

		Button reTing = (Button) this.findViewById(view, R.id.button_reting);
		reTing.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PinViewResolver.this.controller.reting(pin);
			}
		});

		ToggleButton like = (ToggleButton) this.findViewById(view,
				R.id.button_like);
		like.setChecked(this.controller.getIsLiked(pin));

		like.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ToggleButton button = (ToggleButton) v;
				if (button.isChecked()) {
					boolean success = PinViewResolver.this.controller.like(pin);
					if (!success) {
						button.setChecked(success);
					}
				} else {
					PinViewResolver.this.controller.unlike(pin, PinViewResolver.this.context);
				}

			}
		});

		Button share = (Button) this.findViewById(view, R.id.button_share);

		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PinViewResolver.this.controller.share(pin, PinViewResolver.this.context);
			}
		});

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
