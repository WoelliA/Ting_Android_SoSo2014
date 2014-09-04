package de.ur.mi.android.ting.app.fragments;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.adapters.PagingListAdapterBase;
import de.ur.mi.android.ting.app.adapters.ViewCreationDelegatingPagingListAdapter;
import de.ur.mi.android.ting.model.IPaging;
import de.ur.mi.android.ting.utilities.view.ViewResolver;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SearchResultFragment<T> extends FragmentBase {

	private String title;

	private ViewResolver<T> viewResolver;

	private IPaging<T> pagingController;

	public SearchResultFragment(String title, ViewResolver<T> viewResolver,
			IPaging<T> pagingController) {
		this.title = title;
		this.viewResolver = viewResolver;
		this.pagingController = pagingController;
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

		PagingListAdapterBase<T> resultAdapter = new ViewCreationDelegatingPagingListAdapter<T>(
				context, this.viewResolver, this.pagingController);
		ListView listView = (ListView) this.getView().findViewById(
				R.id.search_result_list);
		listView.setAdapter(resultAdapter);
	}
}
