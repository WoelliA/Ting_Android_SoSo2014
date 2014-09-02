package de.ur.mi.android.ting.app.fragments;

import javax.inject.Inject;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.activities.ViewResolver;
import de.ur.mi.android.ting.app.activities.ViewResolver.PinViewResolver;
import de.ur.mi.android.ting.app.adapters.SearchResultsAdapter;
import de.ur.mi.android.ting.model.IPaging;
import de.ur.mi.android.ting.model.ISearchService;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.model.primitives.SearchType;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SearchResultFragment<TResult> extends FragmentBase  {
	private String title;

	@Inject
	public ISearchService searchService;

	private SearchResultsAdapter<TResult> resultAdapter;
	
	public SearchResultFragment(String title, SearchType type, Context context,ViewResolver<TResult> viewResolver) {
		this.title = title;
		this.resultAdapter = new SearchResultsAdapter<TResult>(type, context, viewResolver);
	}

	public String getTitle() {
		return this.title;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view =  inflater.inflate(R.layout.fragment_search, container, false);
		TextView label = (TextView) view.findViewById(R.id.section_label);
		label.setText(this.getTitle());
		return view;
	}

}
