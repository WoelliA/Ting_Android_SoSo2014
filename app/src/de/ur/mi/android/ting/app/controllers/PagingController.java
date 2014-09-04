package de.ur.mi.android.ting.app.controllers;

import java.util.List;

import android.widget.ArrayAdapter;
import de.ur.mi.android.ting.app.adapters.PagingListAdapterBase;
import de.ur.mi.android.ting.model.IPaging;
import de.ur.mi.android.ting.model.PagingResult;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public abstract class PagingController<T> implements IPaging<T> {

	private ArrayAdapter<T> adapter;

	@Override
	public void loadNextPage(final IDoneCallback<PagingResult<T>> doneCallback) {
		this.loadNextPage(this.adapter.getCount(),
				new SimpleDoneCallback<PagingResult<T>>() {

					@Override
					public void done(PagingResult<T> result) {
						List<T> results = result.getResults();
						if (results != null) {
							PagingController.this.adapter.addAll(results);
						}
						doneCallback.done(result);
					}
				});
	}

	protected abstract void loadNextPage(int offset,
			IDoneCallback<PagingResult<T>> doneCallback);

	@Override
	public void setAdapter(PagingListAdapterBase<T> adapter) {
		this.adapter = adapter;
	}

	protected void reset() {
		if (this.adapter == null) {
			return;
		}
		this.adapter.clear();
	}
}
