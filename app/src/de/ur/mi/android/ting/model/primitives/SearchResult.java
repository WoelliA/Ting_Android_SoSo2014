package de.ur.mi.android.ting.model.primitives;

import java.util.List;

import de.ur.mi.android.ting.model.PagingResult;

public class SearchResult<T> extends PagingResult<T> {

	public SearchResult(List<T> results, int requestedAmount) {
		super(requestedAmount, results);

	}
	
	@Override
	public List<T> getResults(){
		return this.results;
	}
}
