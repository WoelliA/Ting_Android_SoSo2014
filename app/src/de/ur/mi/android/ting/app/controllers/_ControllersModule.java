package de.ur.mi.android.ting.app.controllers;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.app.adapters.PinListAdapter;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.ISearchService;

@Module(injects = { PinListAdapter.class }, complete = false, library = true)
public class _ControllersModule {

	private CategoriesController categoryController;

	@Provides
	public SearchController provideSearchController(ISearchService searchService) {
		return new SearchController(searchService);
	}

	@Provides
	@Singleton
	public CategoriesController provideCategoriesController(
			ICategoryProvider categoryProvider) {
		if (this.categoryController == null)
			this.categoryController = new CategoriesController(categoryProvider);
		return categoryController;
	}
}
