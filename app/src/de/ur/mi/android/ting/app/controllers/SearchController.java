package de.ur.mi.android.ting.app.controllers;

import java.util.ArrayList;
import java.util.List;

import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.model.IPaging;
import de.ur.mi.android.ting.model.ISearchService;
import de.ur.mi.android.ting.model.ITypedSearchService;
import de.ur.mi.android.ting.model.primitives.SearchType;

public class SearchController {

	private List<IChangeListener<String>> queryChangeListeners;
	private String query;
	private ISearchService searchService;

	public SearchController(ISearchService typedSearchService) {
		this.searchService = typedSearchService;
		this.queryChangeListeners = new ArrayList<IChangeListener<String>>();
	}

	private void addQueryChangeListener(
			IChangeListener<String> queryChangeListener) {
		this.queryChangeListeners.add(queryChangeListener);
	}
	
	public <T> IPaging<T> getPagingController(SearchType type){
		TypedSearchController<T> controller = new TypedSearchController<T>(this.searchService, type, this.query);
		this.addQueryChangeListener(controller);
		return controller;
	}

	public void onNewQuery(String newQuery) {
		this.query = newQuery;
		for (IChangeListener<String> listener : this.queryChangeListeners) {
			listener.onChange(newQuery);
		}
	}

	public CharSequence getQuery() {
		return this.query;
	}
}
