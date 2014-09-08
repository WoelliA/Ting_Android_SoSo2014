package de.ur.mi.android.ting.app.fragments;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.adapters.ViewCreationDelegatingListAdapter;
import de.ur.mi.android.ting.model.PinData;
import de.ur.mi.android.ting.utilities.LoadedImageData;
import de.ur.mi.android.ting.utilities.view.ViewResolver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class SelectPinImageFragment extends ListFragment<PinData> {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_selectpinimage, container,
				false);
	}

	@Override
	protected ListView getListView(View view) {
		return (ListView) view.findViewById(R.id.selectpinimage_listview);
	}

	@Override
	protected ArrayAdapter<PinData> getListAdapter() {
		ViewResolver<PinData> imageViewResolver = new ViewResolver<PinData>(
				R.layout.pinnable_layout, this.getActivity()) {
			@Override
			protected void decorateView(View view, PinData dataItem,
					ViewGroup parent) {
				ImageView imageView = (ImageView) view;
				imageView.setImageDrawable(null);
				LoadedImageData imageData = dataItem.getImageData();
				imageView.setImageBitmap(imageData.getBitmap());
				imageView.setTag(imageData.getimageUrl());
			}

			@Override
			public boolean skipInject() {
				return true;
			}
		};
		return new ViewCreationDelegatingListAdapter<PinData>(
				this.getActivity(), imageViewResolver);
	}

	@Override
	protected boolean skipInject() {
		return true;
	}
}
