package de.ur.mi.android.ting.app.adapters;

import de.ur.mi.android.ting.model.IPaging;
import de.ur.mi.android.ting.utilities.view.ViewResolver;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class ViewCreationDelegatingPagingListAdapter<T> extends
		PagingListAdapterBase<T> {

	private ViewResolver<T> viewResolver;

	public ViewCreationDelegatingPagingListAdapter(Context context,
			IPaging<T> paging, ViewResolver<T> viewResolver) {
		super(context, paging);
		this.viewResolver = viewResolver;
	}

	@Override
	public View getInfiniteScrollListView(int position, View convertView,
			ViewGroup parent) {
		return this.viewResolver.resolveView(this.getItem(position), convertView, parent);
	}
}
