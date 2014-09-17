package de.ur.mi.android.ting.app.controllers;

import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.model.ISearchService;
import de.ur.mi.android.ting.model.PagingResult;
import de.ur.mi.android.ting.model.primitives.SearchRequest;
import de.ur.mi.android.ting.model.primitives.SearchResult;
import de.ur.mi.android.ting.model.primitives.SearchType;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public class TypedSearchController<T> extends PagingController<T> implements
		IChangeListener<String> {

	private ISearchService searchService;
	private SearchType type;

	private int pageSize = 10;
	private String query;

	public TypedSearchController(ISearchService searchService, SearchType type,
			String query) {
		this.type = type;
		this.searchService = searchService;
		this.query = query;
	}

	@Override
	public void onChange(String newQuery) {
		if (this.query == newQuery) {
			return;
		}
		this.query = newQuery;
		this.reset();		
	}

	@Override
	protected void loadNextPage(int offset,
			final IDoneCallback<PagingResult<T>> doneCallback) {
		SearchRequest request = new SearchRequest(this.type, offset,
				this.pageSize, this.query);
		this.searchService.search(request,
				new SimpleDoneCallback<SearchResult<T>>() {

					@Override
					public void done(SearchResult<T> result) {
						doneCallback.done(result);
					}
				});

	}
}