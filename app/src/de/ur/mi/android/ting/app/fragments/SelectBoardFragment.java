package de.ur.mi.android.ting.app.fragments;

import javax.inject.Inject;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.adapters.ViewCreationDelegatingListAdapter;
import de.ur.mi.android.ting.app.controllers.UserBoardsController;
import de.ur.mi.android.ting.app.viewResolvers.BoardViewResolver;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.utilities.view.ViewResolver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SelectBoardFragment extends ListFragment<Board> {

	@Inject
	public UserBoardsController controller;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.controller.setView(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater
				.inflate(R.layout.fragment_selectboard, container, false);
	}

	@Override
	protected ArrayAdapter<Board> getListAdapter() {
		ViewResolver<Board> viewResolver = new BoardViewResolver(this.getActivity());
		return new ViewCreationDelegatingListAdapter<Board>(this.getActivity(),
				viewResolver);
	}

	@Override
	protected ListView getListView(View view) {
		return (ListView) view.findViewById(R.id.select_board_listview);
	}
}
