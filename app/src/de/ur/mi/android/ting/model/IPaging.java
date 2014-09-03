package de.ur.mi.android.ting.model;

import de.ur.mi.android.ting.utilities.IDoneCallback;

public interface IPaging<T> {
	public void loadNextPage(int offset, IDoneCallback<PagingResult<T>> doneCallback);
}
