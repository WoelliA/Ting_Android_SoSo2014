package de.ur.mi.android.ting.app.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import ca.weixiao.widget.InfiniteScrollListAdapter;
import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.model.IPaging;
import de.ur.mi.android.ting.model.PagingResult;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public abstract class PagingListAdapterBase<T> extends
		InfiniteScrollListAdapter<T> {

	private IPaging<T> paging;
	private IChangeListener<Boolean> hasItemsListener;

	public PagingListAdapterBase(Context context, IPaging<T> paging) {
		super(context, 0, new ArrayList<T>());
		this.paging = paging;
		this.paging.setAdapter(this);
		this.onScrollNext();
	}
	
	public void setHasItemsListener(IChangeListener<Boolean> listener){
		this.hasItemsListener = listener;
	}
	
	@Override
	public void clear() {
		super.clear();
	}

	@Override
	protected void onScrollNext() {
		this.lock();
		if(this.paging == null) {
			return;
		}
		
		if(this.hasItemsListener != null){
			this.hasItemsListener.onChange(true);
		}
		
		this.paging.loadNextPage(new SimpleDoneCallback<PagingResult<T>>() {
			
			@Override
			public void done(PagingResult<T> result) {
				if(hasItemsListener != null){
					boolean hasItems = !PagingListAdapterBase.this.isEmpty() && result.getResults().size() != 0;
					hasItemsListener.onChange(hasItems);
				}
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
