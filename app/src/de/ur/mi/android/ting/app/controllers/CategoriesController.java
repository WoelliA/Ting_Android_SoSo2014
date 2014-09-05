package de.ur.mi.android.ting.app.controllers;

import java.util.List;
import java.util.function.BiConsumer;

import javax.inject.Singleton;

import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.app.ISelectedListener;
import de.ur.mi.android.ting.app.fragments.CategoriesFragment;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.utilities.IBiChangeListener;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;
import android.widget.ArrayAdapter;

@Singleton
public class CategoriesController implements
		IBiChangeListener<Category, Boolean> {

	private ISelectedListener<Category> selectedCategoryChangedListener;

	private ArrayAdapter<Category> adapter;

	private ICategoryProvider categoryProvider;

	protected List<Category> categories;

	public CategoriesController(ICategoryProvider categoryProvider) {
		this.categoryProvider = categoryProvider;
		this.categoryProvider
				.setCategoryFavoriteChangeListener(new IChangeListener<List<Category>>() {

					@Override
					public void onChange(List<Category> changed) {
						initAdapter();
					}
				});
		this.initCategories();
	}

	public void initCategories() {
		this.categoryProvider
				.getAllCategories(new SimpleDoneCallback<List<Category>>() {
					@Override
					public void done(List<Category> result) {
						categories = result;
						initAdapter();
						onCategorySelected(categories.get(0));
					}
				});
	}

	protected void initAdapter() {
		if (this.adapter == null || this.categories == null)
			return;
		sortFavoritesUp();

		this.adapter.clear();
		this.adapter.addAll(categories);
	}

	private void sortFavoritesUp() {
		for (int i = 0; i < categories.size(); i++) {
			Category category = categories.get(i);
			if (category.getIsFavorite()) {
				categories.remove(category);
				categories.add(0, category);
			}
		}
	}

	public void onCategoryIsFavoriteChanged(Category category,
			boolean isFavorite) {
		if (category.getIsFavorite() == isFavorite)
			return;
		this.categoryProvider.saveIsFavoriteCategory(category,
				category.getIsFavorite());
		adjustPosition(category, category.getIsFavorite());
	}

	public void setAdapter(ArrayAdapter<Category> adapter) {
		this.adapter = adapter;
		initAdapter();
	}

	private void adjustPosition(Category category, boolean isChecked) {
		if (this.adapter == null)
			return;

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
		if (this.selectedCategoryChangedListener != null)
			this.selectedCategoryChangedListener.onSelected(selectedCategory);

	}

	@Override
	public void onChange(Category category, Boolean u) {

		this.categoryProvider.saveIsFavoriteCategory(category,
				category.getIsFavorite());
		adjustPosition(category, u);
	}

}
