package de.ur.mi.android.ting.app.fragments;

import de.ur.mi.android.ting.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SearchFragmentUser extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.fragment_search,container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		TextView textView = (TextView)getActivity().findViewById(R.id.section_label);
		textView.setText("User Fragment");
	}
	
	public SearchFragmentUser(){
	}
	
}
