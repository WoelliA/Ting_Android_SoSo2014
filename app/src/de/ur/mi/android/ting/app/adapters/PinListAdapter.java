package de.ur.mi.android.ting.app.adapters;

import java.util.ArrayList;

import javax.inject.Inject;

import ca.weixiao.widget.InfiniteScrollListPageListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.IInjector;
import de.ur.mi.android.ting.model.IPaging;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.utilities.IImageLoadedCallback;
import de.ur.mi.android.ting.utilities.IImageLoader;

public class PinListAdapter extends
		ca.weixiao.widget.InfiniteScrollListAdapter<Pin> {

	private ArrayList<Pin> PinList;
	private Context context;
	private IPaging paging;

	@Inject
	public IImageLoader imageLoader;

	public PinListAdapter(Context context, ArrayList<Pin> Pins, IPaging paging) {
		super(context, R.id.pin_layout, Pins);
		((IInjector) context).inject(this);
		this.paging = paging;
		this.context = context;
		this.PinList = Pins;
	}

	@Override
	protected void onScrollNext() {
		if (this.paging != null) {
			this.paging.loadNextPage();
		}
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

		Pin pin = PinList.get(position);

		if (pin != null) {
			TextView headline = (TextView) v.findViewById(R.id.pin_headline);
			TextView content = (TextView) v.findViewById(R.id.pin_content);
			ImageView picture = (ImageView) v.findViewById(R.id.pin_picture);

			headline.setText(pin.getTitle());
			content.setText(pin.getDescription());
			
			this.imageLoader.loadImage(pin.getImageUri(), picture);
		}

		return v;
	}

}
