package de.ur.mi.android.ting.model;

import java.util.Collection;
import java.util.List;

public class PagingResult<T> {
	protected Collection<T> results;
	private int pageSize;

	public PagingResult(int pageSize, Collection<T> results) {
		this.pageSize = pageSize;
		this.results = results;
	}

	public boolean isCompletePage() {
		return this.results.size() >= this.pageSize;
	}

	public Collection<T> getResults() {
		return this.results;
	}
}
