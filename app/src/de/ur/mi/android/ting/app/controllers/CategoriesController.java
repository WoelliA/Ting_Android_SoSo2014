package de.ur.mi.android.ting.app.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.app.ISelectedListener;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.ISpecialCategories;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.SpecialCategories.SpecialCategory;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.utilities.IBiChangeListener;
import de.ur.mi.android.ting.utilities.IConnectivity;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;
import android.widget.ArrayAdapter;

@Singleton
public class CategoriesController implements
		IBiChangeListener<Category, Boolean> {

	private ISelectedListener<Category> selectedCategoryChangedListener;

	private ArrayAdapter<Category> adapter;

	private ICategoryProvider categoryProvider;

	protected List<Category> categories;

	private IConnectivity connectivity;

	private ISpecialCategories specialCategories;

	private LocalUser user;

	private Category selectedCategory;

	protected Category feedCategory;

	private IUserService userService;


	@Inject
	public CategoriesController(ICategoryProvider categoryProvider,
			IUserService userService, IConnectivity connectivity,
			ISpecialCategories specialCategories, LocalUser user) {
		this.categoryProvider = categoryProvider;
		this.userService = userService;
		this.connectivity = connectivity;
		this.specialCategories = specialCategories;
		this.user = user;
		this.user.addLoginChangeListener(new IChangeListener<LoginResult>() {

			@Override
			public void onChange(LoginResult changed) {
				if (!changed.getIsRightLogin()) {
					this.removeFeedCategory();
					if (CategoriesController.this.feedCategory
							.equals(CategoriesController.this.selectedCategory)) {
						CategoriesController.this
								.onCategorySelected(CategoriesController.this.adapter
										.getItem(0));
					}
				}
			}

			private void removeFeedCategory() {
				Category first = CategoriesController.this.adapter.getItem(0);

				if (first.equals(CategoriesController.this.feedCategory)) {
					CategoriesController.this.adapter.remove(first);
				}
			}
		});
		this.categoryProvider
				.setCategoriesChangedListener(new IChangeListener<Collection<Category>>() {
					@Override
					public void onChange(Collection<Category> changed) {
						CategoriesController.this.initCategories();
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
						
						CategoriesController.this.categories.add(0,
								CategoriesController.this.specialCategories
										.getEverythingCategory());
						if (CategoriesController.this.user.getIsLogedIn()) {
							CategoriesController.this.feedCategory = CategoriesController.this.specialCategories
									.getFeedCategory();

							CategoriesController.this.categories.add(0,
									CategoriesController.this.feedCategory);
						}
						CategoriesController.this.initAdapter();
						if (CategoriesController.this.selectedCategory == null) {
							CategoriesController.this
									.onCategorySelected(CategoriesController.this.categories
											.get(0));
						}
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

	public void setAdapter(ArrayAdapter<Category> adapter) {
		this.adapter = adapter;
		this.initCategories();
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
		this.selectedCategory = selectedCategory;
		if (this.selectedCategoryChangedListener != null) {
			this.selectedCategoryChangedListener.onSelected(selectedCategory);
		}
	}

	@Override
	public void onChange(Category category, Boolean u) {
		this.userService.setIsFavoriteCategory(category,
				category.getIsFavorite());
		this.adjustPosition(category, u);
	}

}
