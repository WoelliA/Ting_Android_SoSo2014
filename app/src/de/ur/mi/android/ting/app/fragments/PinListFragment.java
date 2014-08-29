package de.ur.mi.android.ting.app.fragments;

import java.util.ArrayList;

import javax.inject.Inject;

import ca.weixiao.widget.InfiniteScrollListPageListener;
import ca.weixiao.widget.InfiniteScrollListView;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.adapters.PinListAdapter;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.IPinProvider;
import de.ur.mi.android.ting.model.IPinReceivedCallback;
import de.ur.mi.android.ting.model.PinRequest;
import de.ur.mi.android.ting.model.Primitives.Category;
import de.ur.mi.android.ting.model.Primitives.Pin;

public class PinListFragment extends BaseFragment implements IPaging {
	private PinListAdapter pinAdapter;
	private ArrayList<Pin> pins;

	@Inject
	public IPinProvider pinProvider;
	@Inject
	public ICategoryProvider categoryProvider;

	private String categoryName;
	private Category category;

	public PinListFragment(String categoryName) {
		this.categoryName = categoryName;
		pins = new ArrayList<Pin>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_pinlist, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		this.category = this.categoryProvider
				.resolveCategoryByName(categoryName);
		
		initPinListUI();
		getPins();
	}

	private void getPins() {
		final int count = 10;
		int offset = this.pins.size();
		PinRequest request = new PinRequest(offset, count);
		this.pinProvider.getPinsForCategory(category, request,
				new IPinReceivedCallback() {
					@Override
					public void onPinsReceived(ArrayList<Pin> pins) {
						if(pins != null){
							pinAdapter.addAll(pins);						
						}
						if(pins != null && pins.size() >= count){
							pinAdapter.notifyHasMore();
						} else{
							pinAdapter.notifyEndOfList();
						}
					}
				});
	}

	private void initPinListUI() {
		initPinList();
		initLikeButton();
		initRetingButton();
		pinAdapter.notifyDataSetChanged();
	}

	private void initPinList() {
		pinAdapter = new PinListAdapter(this.getActivity(), pins, this);
		InfiniteScrollListView PinList = (InfiniteScrollListView) getView().findViewById(R.id.list);
		PinList.setAdapter(pinAdapter);
	}

	private void initLikeButton() {
		Button like = (Button) getView().findViewById(R.id.button_like);
	}

	private void initRetingButton() {
		Button reting = (Button) getView().findViewById(R.id.button_reting);
	}

	@Override
	public void loadNextPage() {
		this.pinAdapter.lock();
		this.getPins();		
	}

}
