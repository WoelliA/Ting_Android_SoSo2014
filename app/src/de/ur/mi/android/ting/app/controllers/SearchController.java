package de.ur.mi.android.ting.app.controllers;

import java.util.ArrayList;
import java.util.List;

import de.ur.mi.android.ting.app.IChangeListener;

public class SearchController {

	private List<IChangeListener<String>> queryChangeListeners;
	private String query;

	public SearchController() {
		this.queryChangeListeners = new ArrayList<IChangeListener<String>>();
	}

	public void addQueryChangeListener(
			IChangeListener<String> queryChangeListener) {
		this.queryChangeListeners.add(queryChangeListener);
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
