package de.ur.mi.android.ting.model.dummy;

import de.ur.mi.android.ting.model.ISearchService;
import de.ur.mi.android.ting.model.primitives.SearchRequest;
import de.ur.mi.android.ting.model.primitives.SearchResult;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public class DummySearchService implements ISearchService {

	@Override
	public <T> void search(SearchRequest request,
			IDoneCallback<SearchResult<T>> callback) {
		// TODO Auto-generated method stub

	}

}
