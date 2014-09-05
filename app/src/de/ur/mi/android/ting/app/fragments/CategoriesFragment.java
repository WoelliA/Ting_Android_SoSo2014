package de.ur.mi.android.ting.app.fragments;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.inject.Inject;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.app.ISelectedListener;
import de.ur.mi.android.ting.app.adapters.CategoriesListAdapter;
import de.ur.mi.android.ting.app.controllers.CategoriesController;
import de.ur.mi.android.ting.app.viewResolvers.CategoryViewResolver;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public class CategoriesFragment extends FragmentBase {

	private ListView categoriesListView;

	@Inject
	public CategoriesController controller;

	private CategoriesListAdapter categoriesAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.initUi(this.getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_categories, container, false);
	}


	private void initUi(Activity parent) {
			
		
		this.categoriesListView = (ListView) parent
				.findViewById(R.id.categories_list);
		
		CategoryViewResolver categoryViewResolver = new CategoryViewResolver(
				R.layout.category_layout, this.getActivity(), this.controller);

		this.categoriesAdapter = new CategoriesListAdapter(
				this.getActivity(), categoryViewResolver);
		this.controller.setAdapter(categoriesAdapter);
		
		this.categoriesListView.setAdapter(categoriesAdapter);	
		
		this.categoriesListView
				.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						Category selectedCategory = CategoriesFragment.this.categoriesAdapter
								.getItem(position);
						CategoriesFragment.this.controller.onCategorySelected(selectedCategory);

					}
				});
	}
}
