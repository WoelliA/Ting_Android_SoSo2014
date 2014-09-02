package de.ur.mi.android.ting.model;

import java.util.List;

import de.ur.mi.android.ting.utilities.IDoneCallback;

public interface IPaging<T> {
	public void loadNextPage(int offset, IDoneCallback<List<T>> doneCallback);

	public int getPageSize();
}
