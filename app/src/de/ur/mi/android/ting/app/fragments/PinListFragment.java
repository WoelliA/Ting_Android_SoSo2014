package de.ur.mi.android.ting.app.fragments;

import java.util.ArrayList;

import javax.inject.Inject;

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

public class PinListFragment extends BaseFragment {

	private ArrayList<Pin> aList = null;
	private PinListAdapter alAdapter;
	private Context context;

	@Inject
	public IPinProvider pinProvider;
	@Inject
	public ICategoryProvider categoryProvider;

	private String categoryName;
	private Category category;

	public PinListFragment(String categoryName) {
		this.categoryName = categoryName;
		aList = new ArrayList<Pin>();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.category = this.categoryProvider
				.resolveCategoryByName(categoryName);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_pinlist, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		context = getActivity();
		initPinListUI();

		getPins(0, 10);
	}

	private void getPins(int offset, int count) {
		PinRequest request = new PinRequest(offset, count);
		this.pinProvider.getPinsForCategory(category, request,
				new IPinReceivedCallback() {

					@Override
					public void onPinsReceived(ArrayList<Pin> pins) {
						aList.addAll(pins);
						alAdapter.notifyDataSetChanged();
					}
				});
	}

	private void initPinListUI() {
		initPinList();
		initLikeButton();
		initRetingButton();
		alAdapter.notifyDataSetChanged();
	}

	private void initPinList() {
		alAdapter = new PinListAdapter(context, aList);
		ListView PinList = (ListView) getView().findViewById(R.id.list);
		PinList.setAdapter(alAdapter);
	}

	private void initLikeButton() {
		Button like = (Button) getView().findViewById(R.id.button_like);
	}

	private void initRetingButton() {
		Button reting = (Button) getView().findViewById(R.id.button_reting);
	}

}
