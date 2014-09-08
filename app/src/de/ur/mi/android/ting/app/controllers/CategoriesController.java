package de.ur.mi.android.ting.app.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.app.ISelectedListener;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.utilities.IBiChangeListener;
import de.ur.mi.android.ting.utilities.IConnectivity;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;
import android.widget.ArrayAdapter;

public class CategoriesController implements
		IBiChangeListener<Category, Boolean> {

	private ISelectedListener<Category> selectedCategoryChangedListener;

	private ArrayAdapter<Category> adapter;

	private ICategoryProvider categoryProvider;

	protected List<Category> categories;

	private IConnectivity connectivity;

	public CategoriesController(ICategoryProvider categoryProvider,
			IConnectivity connectivity) {
		this.categoryProvider = categoryProvider;
		this.connectivity = connectivity;
		this.categoryProvider
				.setCategoryFavoriteChangeListener(new IChangeListener<List<Category>>() {
					@Override
					public void onChange(List<Category> changed) {
						CategoriesController.this.initAdapter();
					}
				});
	}

	public void initCategories() {
		if (!this.connectivity.hasWebAccess(true)) {
			return;
		}
		this.categoryProvider
				.getAllCategories(new SimpleDoneCallback<List<Category>>() {
					@Override
					public void done(List<Category> result) {
						CategoriesController.this.categories = result;
						CategoriesController.this.initAdapter();
						CategoriesController.this
								.onCategorySelected(CategoriesController.this.categories
										.get(0));
					}
				});
	}

	protected void initAdapter() {
		if (this.adapter == null || this.categories == null) {
			return;
		}
		this.sortFavoritesUp();

		this.adapter.clear();
		this.adapter.addAll(this.categories);
	}

	private void sortFavoritesUp() {
		for (int i = 0; i < this.categories.size(); i++) {
			Category category = this.categories.get(i);
			if (category.getIsFavorite()) {
				this.categories.remove(category);
				this.categories.add(0, category);
			}
		}
	}

	public void onCategoryIsFavoriteChanged(Category category,
			boolean isFavorite) {
		if (category.getIsFavorite() == isFavorite) {
			return;
		}
		this.categoryProvider.saveIsFavoriteCategory(category,
				category.getIsFavorite());
		this.adjustPosition(category, category.getIsFavorite());
	}

	public void setAdapter(ArrayAdapter<Category> adapter) {
		this.adapter = adapter;
		this.initAdapter();
	}

	private void adjustPosition(Category category, boolean isChecked) {
		if (this.adapter == null) {
			return;
		}

		this.adapter.remove(category);

		if (isChecked) {
			this.adapter.insert(category, 0);
		} else {
			for (int i = 0; i < this.adapter.getCount(); i++) {
				Category c = this.adapter.getItem(i);
				if (c.getIsFavorite()) {
					continue;
				}
				this.adapter.insert(category, i);
				break;
			}
		}
	}

	public void setSelectedCategoryChangeListener(
			ISelectedListener<Category> selectedListener) {
		this.selectedCategoryChangedListener = selectedListener;
	}

	public void onCategorySelected(Category selectedCategory) {
		if (this.selectedCategoryChangedListener != null) {
			this.selectedCategoryChangedListener.onSelected(selectedCategory);
		}

	}

	@Override
	public void onChange(Category category, Boolean u) {

		this.categoryProvider.saveIsFavoriteCategory(category,
				category.getIsFavorite());
		this.adjustPosition(category, u);
	}

}
