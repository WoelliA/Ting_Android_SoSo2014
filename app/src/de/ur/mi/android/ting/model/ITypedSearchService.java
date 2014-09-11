package de.ur.mi.android.ting.model;

import de.ur.mi.android.ting.model.primitives.SearchRequest;
import de.ur.mi.android.ting.model.primitives.SearchResult;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public interface ITypedSearchService<T> {
	public <T> void search(SearchRequest request, IDoneCallback<SearchResult<T>> callback);
}
