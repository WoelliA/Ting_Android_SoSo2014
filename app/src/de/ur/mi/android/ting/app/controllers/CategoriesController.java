package de.ur.mi.android.ting.app.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.app.ISelectedListener;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.ISpecialCategories;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.SpecialCategories.SpecialCategory;
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

	private ISpecialCategories specialCategories;

	private LocalUser user;

	public CategoriesController(ICategoryProvider categoryProvider,
			IConnectivity connectivity, ISpecialCategories specialCategories,
			LocalUser user) {
		this.categoryProvider = categoryProvider;
		this.connectivity = connectivity;
		this.specialCategories = specialCategories;
		this.user = user;
		this.categoryProvider
				.setCategoriesChangedListener(new IChangeListener<Collection<Category>>() {
					@Override
					public void onChange(Collection<Category> changed) {
						CategoriesController.this.initAdapter();
					}
				});
	}

	public void initCategories() {
		if (!this.connectivity.hasWebAccess(true)) {
			return;
		}
		this.categoryProvider
				.getAllCategories(new SimpleDoneCallback<Collection<Category>>() {
					@Override
					public void done(Collection<Category> result) {
						CategoriesController.this.categories = new ArrayList<Category>(
								result);

						categories.add(0,
								specialCategories.getEverythingCategory());
						if (user.getIsLogedIn()) {
							categories.add(0,
									specialCategories.getFeedCategory());
						}
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
		int insertAt = 0;
		for (int i = 0; i < this.categories.size(); i++) {
			Category category = this.categories.get(i);
			if (category instanceof SpecialCategory) {
				insertAt++;
				continue;
			}
			if (category.getIsFavorite()) {
				this.categories.remove(category);
				this.categories.add(insertAt, category);
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

		for (int i = 0; i < this.adapter.getCount(); i++) {
			Category c = this.adapter.getItem(i);
			if (c.getIsFavorite() || c instanceof SpecialCategory) {
				continue;
			}
			if (isChecked) {
				this.adapter.insert(category, i);
				break;
			} else {
				if (c.getName().compareTo(category.getName()) > 0) {
					this.adapter.insert(category, i);
					break;
				}
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
