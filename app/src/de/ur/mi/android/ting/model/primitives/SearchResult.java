package de.ur.mi.android.ting.model.primitives;

import java.util.List;

public class SearchResult<T> {
	
	private List<T> results;
	public SearchResult(List<T> results) {
		this.results = results;
	
	}
	public List<T> getResults(){
		return results;
	}
}
