package de.ur.mi.android.ting.app.fragments;

import java.util.ArrayList;
import javax.inject.Inject;

import ca.weixiao.widget.InfiniteScrollListView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewSwitcher;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.adapters.PinListAdapter;
import de.ur.mi.android.ting.model.IPaging;
import de.ur.mi.android.ting.model.IPinProvider;
import de.ur.mi.android.ting.model.IPinReceivedCallback;
import de.ur.mi.android.ting.model.PagingResult;
import de.ur.mi.android.ting.model.PinRequest;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.views.Loading;

public class PinListFragment extends FragmentBase implements IPaging<Pin> {
	private PinListAdapter pinAdapter;
	@Inject
	public IPinProvider pinProvider;

	private Category category;

	private ViewSwitcher switcher;

	private boolean loading;
	private int requestCount = 10;

	public PinListFragment(Category category) {
		this.category = category;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_pinlist, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.initViewSwitcher();
		this.setLoading(this.loading);
		this.initPinListUI();
	}

	private void initViewSwitcher() {
		View loadingView = Loading.getView(this.getActivity(), "Loading");
		this.switcher = (ViewSwitcher) this.getView().findViewById(
				R.id.fragment_pinlist_viewswitcher);
		this.switcher.addView(loadingView);
	}

	private void getPins(int offset,
			final IDoneCallback<PagingResult<Pin>> doneCallback) {
		PinRequest request = new PinRequest(offset, this.requestCount);
		this.pinProvider.getPinsForCategory(this.category, request,
				new IPinReceivedCallback() {
					@Override
					public void onPinsReceived(ArrayList<Pin> pins) {
						PinListFragment.this.setLoading(false);
						if (doneCallback != null) {
							doneCallback.done(new PagingResult<Pin>(
									PinListFragment.this.requestCount, pins));
						}
					}
				});
	}

	private void setLoading(boolean loading) {
		if (this.loading != loading) {
			this.switcher.showNext();
		}
		this.loading = loading;
	}

	private void initPinListUI() {
		this.initPinList();
		this.initLikeButton();
		this.initRetingButton();
	}

	private void initPinList() {
		this.pinAdapter = new PinListAdapter(this.getActivity(), this);
		InfiniteScrollListView pinList = (InfiniteScrollListView) this.getView()
				.findViewById(R.id.list);
		pinList.setAdapter(this.pinAdapter);
	}

	private void initLikeButton() {
		Button like = (Button) this.getView().findViewById(R.id.button_like);
	}

	private void initRetingButton() {
		Button reting = (Button) this.getView().findViewById(R.id.button_reting);
	}

	@Override
	public void loadNextPage(int offset,
			IDoneCallback<PagingResult<Pin>> doneCallback) {
		this.getPins(offset, doneCallback);

	}
}
