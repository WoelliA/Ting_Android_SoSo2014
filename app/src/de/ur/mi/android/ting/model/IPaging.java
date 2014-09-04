package de.ur.mi.android.ting.model;

import de.ur.mi.android.ting.app.adapters.PagingListAdapterBase;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public interface IPaging<T> {
	public void loadNextPage(IDoneCallback<PagingResult<T>> doneCallback);

	public void setAdapter(PagingListAdapterBase<T> adapter);
}
