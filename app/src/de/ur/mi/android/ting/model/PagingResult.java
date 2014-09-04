package de.ur.mi.android.ting.model;

import java.util.List;

public class PagingResult<T> {
	protected List<T> results;
	private int pageSize;

	public PagingResult(int pageSize, List<T> results) {
		this.pageSize = pageSize;
		this.results = results;
	}

	public boolean isCompletePage() {
		return this.results.size() >= this.pageSize;
	}

	public List<T> getResults() {
		return this.results;
	}
}
