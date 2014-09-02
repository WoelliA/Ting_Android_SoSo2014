package de.ur.mi.android.ting.app.adapters;
import java.util.ArrayList;

import ca.weixiao.widget.InfiniteScrollListAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import de.ur.mi.android.ting.app.activities.ViewResolver;
import de.ur.mi.android.ting.model.primitives.SearchType;

public class SearchResultsAdapter<TResult> extends InfiniteScrollListAdapter<TResult> {

	private ViewResolver<TResult> viewResolver;
	private SearchType type;

	public SearchResultsAdapter(SearchType type, Context context,
			ViewResolver<TResult> viewResolver) {
		super(context,0, new ArrayList<TResult>());
		this.type = type;
		this.viewResolver = viewResolver;
	}

	@Override
	protected void onScrollNext() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public View getInfiniteScrollListView(int position, View convertView,
			ViewGroup parent) {
		TResult data = this.getItem(position);
		return this.viewResolver.resolveView(data);
	}
}