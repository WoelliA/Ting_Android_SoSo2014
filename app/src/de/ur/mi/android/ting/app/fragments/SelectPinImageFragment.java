package de.ur.mi.android.ting.app.fragments;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.adapters.ViewCreationDelegatingListAdapter;
import de.ur.mi.android.ting.utilities.LoadedImageData;
import de.ur.mi.android.ting.utilities.view.ViewResolver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class SelectPinImageFragment extends ListFragment<LoadedImageData> {

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
	protected ArrayAdapter<LoadedImageData> getListAdapter() {
		ViewResolver<LoadedImageData> imageViewResolver = new ViewResolver<LoadedImageData>(
				R.layout.pinnable_layout, this.getActivity()) {
			@Override
			protected void decorateView(View view, LoadedImageData dataItem,
					ViewGroup parent) {
				ImageView imageView = (ImageView) view;
				imageView.setImageDrawable(null);
				imageView.setImageBitmap(dataItem.getBitmap());
				imageView.setTag(dataItem.getimageUrl());
			}

			@Override
			public boolean skipInject() {
				return true;
			}
		};
		return new ViewCreationDelegatingListAdapter<LoadedImageData>(
				this.getActivity(), imageViewResolver);
	}

	@Override
	protected boolean skipInject() {
		return true;
	}
}
