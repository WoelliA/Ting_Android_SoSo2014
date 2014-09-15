package de.ur.mi.android.ting.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public abstract class CategoryProviderBase implements
		IChangeListener<LoginResult>, ICategoryProvider {

	protected LocalUser user;
	private LinkedHashMap<String, Category> categories;

	private IChangeListener<Collection<Category>> categoryFavorityChangeListener;
	private IUserService userService;

	public CategoryProviderBase(LocalUser user, IUserService userService) {
		this.user = user;
		this.userService = userService;
		this.user.addLoginChangeListener(this);
	}

	protected Collection<Category> getCategories() {
		return this.categories.values();
	}

	@Override
	public void getAllCategories(
			final IDoneCallback<Collection<Category>> callback) {
		if (this.categories != null && this.categories.size() != 0) {
			callback.done(this.categories.values());
			return;
		}
		this.categories = new LinkedHashMap<String, Category>();
		this.getAllCategoriesImpl(new SimpleDoneCallback<List<Category>>() {

			@Override
			public void done(List<Category> result) {
				for (Category category : result) {
					CategoryProviderBase.this.categories.put(category.getId(),
							category);
				}
				callback.done(CategoryProviderBase.this.categories.values());
			}
		});
	}

	protected abstract void getAllCategoriesImpl(
			IDoneCallback<List<Category>> simpleDoneCallback);

	public String[] getCategoryNames(List<Category> categories) {
		String[] categorynames = new String[categories.size()];
		for (int i = 0; i < categories.size(); i++) {
			Category cate = categories.get(i);

			categorynames[i] = (cate.getName());
		}
		return categorynames;
	}

	@Override
	public void onChange(LoginResult result) {
		if (result.getIsRightLogin()) {
			LinkedHashMap<String, Category> oldCategories = new LinkedHashMap<String, Category>(
					this.categories);
			this.categories = new LinkedHashMap<String, Category>();
			this.categories.putAll(oldCategories);

			this.notifyCategoriesChangeListener();
			this.userService
					.getFavoriteCategories(new SimpleDoneCallback<List<Category>>() {

						@Override
						public void done(List<Category> result) {
							for (Category c : result) {
								Category category = CategoryProviderBase.this.categories
										.get(c.getId());
								if (category != null) {
									category.setIsFavorite(true);
								}
							}

							CategoryProviderBase.this
									.notifyCategoriesChangeListener();
						}
					});
		} else {
			for (Category category : this.categories.values()) {
				category.setIsFavorite(false);
			}
			this.notifyCategoriesChangeListener();
		}
	}

	protected void notifyCategoriesChangeListener() {
		if (this.categoryFavorityChangeListener != null) {
			this.categoryFavorityChangeListener.onChange(this.categories
					.values());
		}
	}

	@Override
	public void setCategoriesChangedListener(
			IChangeListener<Collection<Category>> listener) {
		this.categoryFavorityChangeListener = listener;
	}
}
