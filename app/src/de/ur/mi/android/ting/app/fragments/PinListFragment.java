package de.ur.mi.android.ting.app.fragments;

import java.util.ArrayList;

import javax.inject.Inject;

import ca.weixiao.widget.InfiniteScrollListView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.adapters.PinListAdapter;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.IPaging;
import de.ur.mi.android.ting.model.IPinProvider;
import de.ur.mi.android.ting.model.IPinReceivedCallback;
import de.ur.mi.android.ting.model.PinRequest;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.views.Loading;

public class PinListFragment extends BaseFragment implements IPaging {
	private PinListAdapter pinAdapter;
	private ArrayList<Pin> pins;

	@Inject
	public IPinProvider pinProvider;

	private Category category;

	private ViewSwitcher switcher;

	private boolean loading;

	public PinListFragment(Category category) {
		this.category = category;
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

		initViewSwitcher();
		initPinListUI();
		setLoading(true);
		getPins();
	}

	private void initViewSwitcher() {
		View loadingView = Loading.getView(this.getActivity(), "Loading");
		this.switcher = (ViewSwitcher) this.getView().findViewById(
				R.id.fragment_pinlist_viewswitcher);
		this.switcher.addView(loadingView);
	}

	private void getPins() {
		this.pinAdapter.lock();
		final int count = 10;
		int offset = this.pins.size();
		PinRequest request = new PinRequest(offset, count);
		this.pinProvider.getPinsForCategory(category, request,
				new IPinReceivedCallback() {
					@Override
					public void onPinsReceived(ArrayList<Pin> pins) {
						setLoading(false);
						if (pins != null) {
							pinAdapter.addAll(pins);
						}
						if (pins != null && pins.size() >= count) {
							pinAdapter.notifyHasMore();
						} else {
							pinAdapter.notifyEndOfList();
						}
					}

				});
	}

	private void setLoading(boolean loading) {
		if (this.loading != loading)
			this.switcher.showNext();
		this.loading = loading;
	}

	private void initPinListUI() {
		initPinList();
		initLikeButton();
		initRetingButton();
		pinAdapter.notifyDataSetChanged();
	}

	private void initPinList() {
		pinAdapter = new PinListAdapter(this.getActivity(), pins, this);
		InfiniteScrollListView pinList = (InfiniteScrollListView) getView()
				.findViewById(R.id.list);
		pinList.setAdapter(pinAdapter);
	}

	private void initLikeButton() {
		Button like = (Button) getView().findViewById(R.id.button_like);
	}

	private void initRetingButton() {
		Button reting = (Button) getView().findViewById(R.id.button_reting);
	}

	@Override
	public void loadNextPage() {
		this.getPins();
	}

}
