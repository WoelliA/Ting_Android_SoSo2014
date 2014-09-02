package de.ur.mi.android.ting.app.adapters;

import java.util.ArrayList;

import javax.inject.Inject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.IInjector;
import de.ur.mi.android.ting.model.IPaging;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.utilities.IImageLoader;
import de.ur.mi.android.ting.views.Loading;

public class PinListAdapter extends PagingListAdapterBase<Pin> {

	private Context context;

	@Inject
	public IImageLoader imageLoader;

	public PinListAdapter(Context context, ArrayList<Pin> Pins, IPaging<Pin> paging) {
		super(context, R.id.pin_layout, Pins, paging);
		((IInjector) context).inject(this);
		this.context = context;
	}

	@Override
	public View getInfiniteScrollListView(int position, View convertView,
			ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.pin_layout, parent, false);
		}

		Pin pin = this.getItem(position);

		if (pin != null) {
			TextView headline = (TextView) v.findViewById(R.id.pin_headline);
			TextView content = (TextView) v.findViewById(R.id.pin_content);
			ImageView picture = (ImageView) v.findViewById(R.id.pin_picture);

			headline.setText(pin.getTitle());
			content.setText(pin.getDescription());

			ViewSwitcher switcher = (ViewSwitcher) v
					.findViewById(R.id.pin_loading_switcher);

			String imageUri = pin.getImageUri();
			if (switcher.getChildCount() < 2 && !isSameImage(picture, imageUri)) {
				picture.setTag(imageUri);
				this.imageLoader.loadImage(imageUri, picture, switcher,
						Loading.getView(context, ""));
			} else {
			}
		}

		return v;
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
