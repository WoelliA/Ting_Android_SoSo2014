package de.ur.mi.android.ting.app.fragments;

import ca.weixiao.widget.InfiniteScrollListView;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.ISelectedListener;
import de.ur.mi.android.ting.app.adapters.PagingListAdapterBase;
import de.ur.mi.android.ting.app.adapters.ViewCreationDelegatingPagingListAdapter;
import de.ur.mi.android.ting.model.IPaging;
import de.ur.mi.android.ting.utilities.view.Loading;
import de.ur.mi.android.ting.utilities.view.ViewResolver;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchResultFragment<T> extends FragmentBase {

	private String title;

	private ViewResolver<T> viewResolver;

	private IPaging<T> pagingController;

	private ISelectedListener<T> selectedListener;
	
	public SearchResultFragment(){
		
	}

	public SearchResultFragment(String title, ViewResolver<T> viewResolver,
			IPaging<T> pagingController, ISelectedListener<T> onSelect) {
		this.title = title;
		this.viewResolver = viewResolver;
		this.pagingController = pagingController;
		this.selectedListener = onSelect;
	}

	public String getTitle() {
		return this.title;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_search, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.init(this.getActivity());
	}

	private void init(Context context) {
		if (context == null) {
			return;
		}

		final PagingListAdapterBase<T> resultAdapter = new ViewCreationDelegatingPagingListAdapter<T>(
				context, this.viewResolver, this.pagingController);
		InfiniteScrollListView listView = (InfiniteScrollListView) this.getView().findViewById(
				R.id.search_result_list);
		listView.setLoadingView(Loading.getView(this.getActivity(), "Searching..."));
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (SearchResultFragment.this.selectedListener == null) {
					return;
				}
				SearchResultFragment.this.selectedListener.onSelected(resultAdapter.getItem(position));

			}
		});
		listView.setAdapter(resultAdapter);
	}
}
