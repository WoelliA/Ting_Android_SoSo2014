package de.ur.mi.android.ting.app.adapters;

import java.util.ArrayList;
import android.content.Context;
import ca.weixiao.widget.InfiniteScrollListAdapter;
import de.ur.mi.android.ting.model.IPaging;
import de.ur.mi.android.ting.model.PagingResult;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public abstract class PagingListAdapterBase<T> extends
		InfiniteScrollListAdapter<T> {

	private IPaging<T> paging;

	public PagingListAdapterBase(Context context, IPaging<T> paging) {
		super(context, 0, new ArrayList<T>());
		this.paging = paging;
		this.paging.setAdapter(this);
		this.onScrollNext();
	}

	@Override
	protected void onScrollNext() {
		this.lock();
		if(this.paging == null) {
			return;
		}
		
		this.paging.loadNextPage(new SimpleDoneCallback<PagingResult<T>>() {
			@Override
			public void done(PagingResult<T> result) {
				PagingListAdapterBase.this.unlock();
				if (result != null && result.isCompletePage()) {
					PagingListAdapterBase.this.notifyHasMore();
				} else {
					PagingListAdapterBase.this.notifyEndOfList();
				}
			}
		});
	}
}
