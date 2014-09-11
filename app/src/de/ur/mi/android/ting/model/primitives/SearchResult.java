package de.ur.mi.android.ting.model.primitives;

import java.util.Collection;
import java.util.List;

import de.ur.mi.android.ting.model.PagingResult;

public class SearchResult<T> extends PagingResult<T> {

	public SearchResult(Collection<T> collection, int requestedAmount) {
		super(requestedAmount, collection);

	}
}
