package de.ur.mi.android.ting.app.activities;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.Tutorial;
import de.ur.mi.android.ting.app.adapters.ViewCreationDelegatingListAdapter;
import de.ur.mi.android.ting.app.controllers.CategoriesController;
import de.ur.mi.android.ting.app.viewResolvers.CategoryViewResolver;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.utilities.view.ViewResolver;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SelectCategoriesActivity extends BaseActivity implements
		OnClickListener {

	@Inject
	public CategoriesController controller;
	private ArrayAdapter<Category> adapter;
	private Tutorial tutorial;
	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_select_categories);
		this.initUi();
		this.tutorial = Tutorial.getTutorial(getIntent());
	}

	private void initUi() {
		list = (ListView) this.findViewById(R.id.list);
		this.findViewById(R.id.action_continue).setOnClickListener(this);

		ViewResolver<Category> viewResolver = new CategoryViewResolver(
				R.layout.category_layout, this, null) {
			@Override
			protected void decorateView(View view, Category category,
					ViewGroup parent) {
				super.decorateView(view, category, parent);
				this.findViewById(view, R.id.category_favorite_button)
						.setVisibility(View.GONE);
			}
		};
		adapter = new ViewCreationDelegatingListAdapter<Category>(this,
				viewResolver);
		controller.setAdapter(adapter);
		list.setAdapter(adapter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.action_continue:
			if (this.tutorial != null) {
				int len = list.getCount();
				ArrayList<String> selectedCategoryIds = new ArrayList<String>();
				SparseBooleanArray checked = list.getCheckedItemPositions();
				for (int i = 0; i < len; i++)
					if (checked.get(i)) {
						Category item = adapter.getItem(i);
						selectedCategoryIds.add(item.getId());
					}
				this.tutorial.proceed(this,
						new SimpleEntry<String, ArrayList<String>>(
								"selectedcategories", selectedCategoryIds));
			}
			break;
		}

	}
}
