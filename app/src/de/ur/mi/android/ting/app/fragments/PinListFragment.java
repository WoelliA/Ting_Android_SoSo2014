package de.ur.mi.android.ting.app.fragments;

import java.util.ArrayList;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.adapters.PinListAdapter;
import de.ur.mi.android.ting.model.Primitives.Pin;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class PinListFragment extends BaseFragment {

	private ArrayList<Pin> aList = null;
	private PinListAdapter alAdapter;
	private Context context;
	
	public PinListFragment(){
		
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	 
		return inflater.inflate(R.layout.fragment_pinlist, container, false);
	}	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		
		context = getActivity();
		initPinListUI();
		
	}

	private void initPinListUI() {
		initPinList();
		initLikeButton();
		initRetingButton();
		alAdapter.notifyDataSetChanged();
	}

	private void initPinList() {
		alAdapter = new PinListAdapter(context, aList);
		ListView PinList = (ListView)getView().findViewById(R.id.list);
		PinList.setAdapter(alAdapter);
	}
	
	private void initLikeButton() {
		Button like = (Button)getView().findViewById(R.id.button_like);
	}
	
	private void initRetingButton() {
		Button reting = (Button)getView().findViewById(R.id.button_reting);
	}
	
}
