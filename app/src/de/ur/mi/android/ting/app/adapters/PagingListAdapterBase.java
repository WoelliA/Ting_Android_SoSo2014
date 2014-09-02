package de.ur.mi.android.ting.app.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import ca.weixiao.widget.InfiniteScrollListAdapter;
import de.ur.mi.android.ting.model.IPaging;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public abstract class PagingListAdapterBase<T> extends InfiniteScrollListAdapter<T> {

	private IPaging<T> paging;

	public PagingListAdapterBase(Context context, int resource,
			ArrayList<T> items, IPaging<T> paging) {
		super(context, resource, items);
		this.paging = paging;
		onScrollNext();
	}

	@Override
	protected void onScrollNext() {
		this.lock();
		this.paging.loadNextPage(this.getCount(),
				new SimpleDoneCallback<List<T>>() {

					@Override
					public void done(List<T> result) {
						unlock();
						if (result != null) {
							addAll(result);
						}
						if (result != null
								&& result.size() >= paging.getPageSize()) {
							notifyHasMore();
						} else {
							notifyEndOfList();
						}

					}
				});

	}
}
