package de.ur.mi.android.ting.app.controllers;

import java.util.ArrayList;
import java.util.Arrays;
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
import de.ur.mi.android.ting.model.primitives.UniqueBase;
import de.ur.mi.android.ting.utilities.IBiChangeListener;
import de.ur.mi.android.ting.utilities.IConnectivity;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;
import android.widget.ArrayAdapter;

@Singleton
public class CategoriesController {

	protected ArrayAdapter<Category> adapter;
	protected ICategoryProvider categoryProvider;
	private static Category[] categories;
	private IConnectivity connectivity;
	protected Category selectedCategory;
	protected IUserService userService;
	protected LocalUser user;

	public Category getSelectedCategory() {
		return this.selectedCategory;
	}

	@Inject
	public CategoriesController(ICategoryProvider categoryProvider,
			IConnectivity connectivity, IUserService userService, LocalUser user) {
		this.categoryProvider = categoryProvider;
		this.connectivity = connectivity;
		this.userService = userService;
		this.user = user;
	}

	public void initCategories() {
		if (categories != null) {
			this.onCategoriesReceived(Arrays.asList(categories));
			return;
		}
		if (!this.connectivity.hasWebAccess(true)) {
			return;
		}
		this.categoryProvider
				.getAllCategories(new SimpleDoneCallback<Collection<Category>>() {
					@Override
					public void done(Collection<Category> result) {
						Category[] cs = new Category[result.size()];
						categories = result.toArray(cs);
						onCategoriesReceived(result);
					}
				});
	}

	protected void onCategoriesReceived(Collection<Category> result) {
		this.initAdapter(new ArrayList<Category>(result));
	}

	protected void initAdapter(List<Category> categories) {
		if (this.adapter == null || categories == null) {
			return;
		}

		this.adapter.clear();
		this.adapter.addAll(categories);
	}

	public void setAdapter(ArrayAdapter<Category> adapter) {
		this.adapter = adapter;
		this.initCategories();
	}

	public void saveFavoriteCategories(final Collection<Category> collection) {
		this.userService.saveFavoriteCategories(UniqueBase.getIds(collection));
		user.setFavoriteCategories(collection);
	}

	public Collection<Category> getCategories() {
		return Arrays.asList(categories);
	}

	public Collection<Category> getCategoriesForIds(
			Collection<String> categoryIds) {

		ArrayList<Category> list = new ArrayList<Category>();
		for (int i = 0; i < categories.length; i++) {
			Category category = categories[i];
			if (categoryIds.contains(category.getId()))
				;
			{
				list.add(category);
			}
		}
		return list;
	}
}
