package de.ur.mi.android.ting.app.fragments;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.AbstractMap.SimpleEntry;

import javax.inject.Inject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.Tutorial;
import de.ur.mi.android.ting.app.adapters.ViewCreationDelegatingListAdapter;
import de.ur.mi.android.ting.app.controllers.CategoriesController;
import de.ur.mi.android.ting.app.controllers.MultiSelectCategoriesController;
import de.ur.mi.android.ting.app.viewResolvers.CategoryViewResolver;
import de.ur.mi.android.ting.app.viewResolvers.MultiSelectCategoryViewResolver;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.UniqueBase;
import de.ur.mi.android.ting.utilities.view.ViewResolver;

public class SelectCategoriesFragment extends FragmentBase {

	@Inject
	public MultiSelectCategoriesController controller;

	private ArrayAdapter<Category> adapter;
	private ListView list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_select_categories, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.initUi();
	}

	private void initUi() {
		this.list = (ListView) this.findViewById(R.id.list);
		this.list.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

		ViewResolver<Category> viewResolver = new MultiSelectCategoryViewResolver(
				R.layout.category_layout, getActivity(), this.controller);

		this.adapter = new ViewCreationDelegatingListAdapter<Category>(
				getActivity(), viewResolver);
		this.controller.setAdapter(adapter);
		this.list.setAdapter(this.adapter);
	}
}
