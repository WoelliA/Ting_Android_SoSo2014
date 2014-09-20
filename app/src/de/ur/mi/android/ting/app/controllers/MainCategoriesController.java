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

@Singleton
public class MainCategoriesController extends CategoriesController implements
		IBiChangeListener<Category, Boolean> {

	private ISpecialCategories specialCategories;
	protected Category feedCategory;
	private ISelectedListener<Category> selectedCategoryChangedListener;

	@Inject
	public MainCategoriesController(ICategoryProvider categoryProvider,
			IUserService userService, IConnectivity connectivity,
			LocalUser user, ISpecialCategories specialCategories) {
		super(categoryProvider, connectivity, userService, user);
		this.specialCategories = specialCategories;

		this.user.addLoginChangeListener(new IChangeListener<LoginResult>() {

			@Override
			public void onChange(LoginResult changed) {
				if (!changed.getIsRightLogin()) {
					removeFeedCategory();
					if (feedCategory.equals(selectedCategory)) {
						onCategorySelected(adapter.getItem(0));
					}
				}
			}
		});

		this.categoryProvider
				.setCategoriesChangedListener(new IChangeListener<Collection<Category>>() {
					@Override
					public void onChange(Collection<Category> changed) {
						initCategories();
					}
				});
	}

	public void onCategorySelected(Category selectedCategory) {
		this.selectedCategory = selectedCategory;
		if (this.selectedCategoryChangedListener != null) {
			this.selectedCategoryChangedListener.onSelected(selectedCategory);
		}
	}

	public void setSelectedCategoryChangeListener(
			ISelectedListener<Category> selectedListener) {
		this.selectedCategoryChangedListener = selectedListener;
	}

	private void removeFeedCategory() {
		Category first = this.adapter.getItem(0);

		if (first.getName().equals(this.feedCategory.getName())) {
			this.adapter.remove(first);
		}
	}

	@Override
	protected void onCategoriesReceived(Collection<Category> result) {
		ArrayList<Category> list = new ArrayList<Category>(result);
		list.add(0, this.specialCategories.getEverythingCategory());
		if (this.user.getIsLogedIn()) {
			this.feedCategory = this.specialCategories.getFeedCategory();
			list.add(0, this.feedCategory);
		}
		super.onCategoriesReceived(list);
	}

	@Override
	protected void initAdapter(List<Category> categories) {
		this.sortFavoritesUp(categories);
		super.initAdapter(categories);
		if (this.selectedCategory == null) {
			this.onCategorySelected(categories.get(0));
		}
	}

	private void sortFavoritesUp(List<Category> categories) {
		int insertAt = 0;
		for (int i = 0; i < categories.size(); i++) {
			Category category = categories.get(i);
			if (category instanceof SpecialCategory) {
				insertAt++;
				continue;
			}
			if (category.getIsFavorite()) {
				categories.remove(category);
				categories.add(insertAt, category);
			}
		}
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

	@Override
	public void onChange(Category category, Boolean isFavorite) {
		this.adjustPosition(category, isFavorite);
		if(isFavorite){
			user.getFavoriteCategories().add(category);
		} else {
			user.getFavoriteCategories().remove(category);
		}
		this.userService.setIsFavoriteCategory(category, isFavorite);
	}
}
