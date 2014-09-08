package de.ur.mi.android.ting.app.fragments;

import ca.weixiao.widget.InfiniteScrollListView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.adapters.PinListAdapter;
import de.ur.mi.android.ting.app.controllers.PinListController;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.views.Loading;

public class PinListFragment extends FragmentBase {
	private PinListAdapter pinAdapter;
	private Category category;

	private ViewSwitcher switcher;

	private boolean loading;
	private PinListController controller;

	public PinListFragment(PinListController controller) {
		this.controller = controller;
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
		this.initPinListUI();
	}

	private void initViewSwitcher() {
		View loadingView = Loading.getView(this.getActivity(), "Loading");
		this.switcher = (ViewSwitcher) this.getView().findViewById(
				R.id.fragment_pinlist_viewswitcher);
		this.switcher.addView(loadingView);
	}

	private void setLoading(boolean loading) {
		if (this.loading != loading) {
			this.switcher.showNext();
		}
		this.loading = loading;
	}

	private void initPinListUI() {
		this.initPinList();
	}

	private void initPinList() {
		this.pinAdapter = new PinListAdapter(this.getActivity(),
				this.controller);
		
		InfiniteScrollListView pinList = (InfiniteScrollListView) this
				.getView().findViewById(R.id.list);
		pinList.setLoadingView(Loading.getView(this.getActivity(), "Loading..."));
		pinList.setAdapter(this.pinAdapter);
		this.controller.setAdapter(this.pinAdapter);
	}
}
