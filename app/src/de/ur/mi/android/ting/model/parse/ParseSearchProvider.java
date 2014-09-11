package de.ur.mi.android.ting.model.parse;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import de.ur.mi.android.ting.model.ISearchService;
import de.ur.mi.android.ting.model.ITypedSearchService;
import de.ur.mi.android.ting.model.primitives.SearchRequest;
import de.ur.mi.android.ting.model.primitives.SearchResult;
import de.ur.mi.android.ting.model.primitives.SearchType;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public class ParseSearchProvider implements ISearchService {

	private HashMap<SearchType, ITypedSearchService<?>> services;

	public ParseSearchProvider(HashMap<SearchType, ITypedSearchService<?>> services) {
		this.services = services;
	}

	@Override
	public <T> void search(SearchRequest request,
			IDoneCallback<SearchResult<T>> callback) {
		SearchType type = request.getType();
		if(services.containsKey(type)){
			services.get(type).search(request, callback);
		}		
		
	}

}
