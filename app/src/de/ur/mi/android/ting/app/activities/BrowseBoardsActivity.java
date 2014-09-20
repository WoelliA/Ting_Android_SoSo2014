package de.ur.mi.android.ting.app.activities;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.adapters.PagingListAdapterBase;
import de.ur.mi.android.ting.app.adapters.ViewCreationDelegatingPagingListAdapter;
import de.ur.mi.android.ting.app.controllers.BoardsController;
import de.ur.mi.android.ting.app.controllers.MultiSelectCategoriesController;
import de.ur.mi.android.ting.app.viewResolvers.SearchResultResolvers.BoardResolver;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.utilities.view.ViewResolver;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class BrowseBoardsActivity extends DrawerActivityBase {

	@Inject
	public BoardsController controller;

	private PagingListAdapterBase<Board> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_browse_boards);
		this.initUi();
		this.controller.setAdapter(adapter);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		this.drawerLayout.openDrawer(Gravity.LEFT);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	private void initUi() {
		ListView list = (ListView) this.findViewById(R.id.list);
		this.adapter = this.createAdapter();
		list.setAdapter(adapter);
	}

	private PagingListAdapterBase<Board> createAdapter() {
		ViewResolver<Board> viewResolver = this.createViewResolver();
		return new ViewCreationDelegatingPagingListAdapter<Board>(this,
				viewResolver, controller);
	}

	private ViewResolver<Board> createViewResolver() {
		return new BoardResolver(this);
	}
}
