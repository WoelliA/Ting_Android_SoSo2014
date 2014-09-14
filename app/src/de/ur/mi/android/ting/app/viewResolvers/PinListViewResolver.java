package de.ur.mi.android.ting.app.viewResolvers;

import javax.inject.Inject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.activities.ShareActivity;
import de.ur.mi.android.ting.app.activities.ShareActivity.ShareStage;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.utilities.IImageLoader;
import de.ur.mi.android.ting.utilities.view.Loading;
import de.ur.mi.android.ting.utilities.view.ViewResolver;

public class PinListViewResolver extends ViewResolver<Pin> {

	@Inject
	public IImageLoader imageLoader;

	private String linkUri;

	public PinListViewResolver(Context context) {
		super(R.layout.pin_layout, context);
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

		Button reTing = (Button) this.findViewById(view, R.id.button_reting);
		reTing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, ShareActivity.class);
				intent.putExtra(ShareActivity.STAGE_KEY, ShareStage.BoardSelect);
				intent.putExtra(ShareActivity.PIN_ID_KEY, pin.getId());
				context.startActivity(intent);
			}
		});

		Button like = (Button) this.findViewById(view, R.id.button_like);
		like.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(PinListViewResolver.this.getContext(),
						"like clicked", Toast.LENGTH_SHORT).show();

			}
		});

		Button share = (Button) this.findViewById(view, R.id.button_share);
		this.linkUri = pin.getLinkUri();
		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String shareText = PinListViewResolver.this.linkUri;

				Intent textShareIntent = new Intent(Intent.ACTION_SEND);
				textShareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
				textShareIntent.setType("text/plain");
				PinListViewResolver.this.getContext().startActivity(
						Intent.createChooser(textShareIntent,
								"Share Link with..."));

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
