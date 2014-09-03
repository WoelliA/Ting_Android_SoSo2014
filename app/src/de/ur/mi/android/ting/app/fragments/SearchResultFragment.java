package de.ur.mi.android.ting.app.fragments;

import javax.inject.Inject;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.app.adapters.ViewCreationDelegatingPagingListAdapter;
import de.ur.mi.android.ting.model.IPaging;
import de.ur.mi.android.ting.model.ISearchService;
import de.ur.mi.android.ting.model.PagingResult;
import de.ur.mi.android.ting.model.primitives.SearchRequest;
import de.ur.mi.android.ting.model.primitives.SearchResult;
import de.ur.mi.android.ting.model.primitives.SearchType;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;
import de.ur.mi.android.ting.utilities.view.ViewResolver;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchResultFragment<T> extends FragmentBase implements
		IPaging<T>, IChangeListener<String> {

	private String title;

	@Inject
	public ISearchService searchService;

	private SearchType type;

	private String query;

	private ViewResolver<T> viewResolver;

	public SearchResultFragment(String title, SearchType type,
			ViewResolver<T> viewResolver) {
		this.title = title;
		this.type = type;
		this.viewResolver = viewResolver;
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
		this.initResults(this.getActivity());
	}

	private void initResults(Context context) {
		if(context == null) {
			return;
		}
		
		ArrayAdapter<T> resultAdapter = new ViewCreationDelegatingPagingListAdapter<T>(
				context, this, this.viewResolver);
		ListView listView = (ListView) this.getView().findViewById(
				R.id.search_result_list);
		listView.setAdapter(resultAdapter);
	}

	@Override
	public void loadNextPage(int offset,
			final IDoneCallback<PagingResult<T>> doneCallback) {
		if (this.query == null) {
			return;
		}

		final int count = 10;
		SearchRequest request = new SearchRequest(this.type, offset, count, this.query);
		this.searchService.search(request,
				new SimpleDoneCallback<SearchResult<T>>() {

					@Override
					public void done(SearchResult<T> result) {
						doneCallback.done(new PagingResult<T>(count, result
								.getResults()));
					}

				});

	}

	@Override
	public void onChange(String newQuery) {
		if (this.query == newQuery) {
			return;
		}
		this.query = newQuery;
		this.initResults(this.getActivity());
	}

}
