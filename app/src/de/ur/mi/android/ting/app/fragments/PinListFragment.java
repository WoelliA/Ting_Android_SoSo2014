package de.ur.mi.android.ting.app.fragments;

import ca.weixiao.widget.InfiniteScrollListView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.adapters.PinListAdapter;
import de.ur.mi.android.ting.app.controllers.PinListController;
import de.ur.mi.android.ting.utilities.view.Loading;

public class PinListFragment extends FragmentBase {
	private PinListAdapter pinAdapter;

	public PinListController controller;

	public PinListFragment() {
	}
	
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
		if(this.controller == null){
			return;
		}
		this.initPinListUI();
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
