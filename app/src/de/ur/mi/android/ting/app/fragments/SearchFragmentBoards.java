package de.ur.mi.android.ting.app.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import de.ur.mi.android.ting.R;

public class SearchFragmentBoards extends Fragment{

	ListView resultList;
	ArrayList<String> searchResults;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.fragment_search,container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		TextView textView = (TextView)getActivity().findViewById(R.id.section_label);
		textView.setText("Board Fragment");
		if (searchResults != null) {
			resultList = (ListView)getActivity().findViewById(R.id.result_list);
			ArrayAdapter aa= new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, searchResults);
			resultList.setAdapter(aa);
		}
	}
	
	public SearchFragmentBoards() {
	}
	
	public static SearchFragmentBoards newInstance(ArrayList<String> searchResults) {
		SearchFragmentBoards fragment = new SearchFragmentBoards();
		fragment.searchResults = searchResults;
		return fragment;
	}
}