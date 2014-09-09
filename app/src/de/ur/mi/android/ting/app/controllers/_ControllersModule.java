package de.ur.mi.android.ting.app.controllers;

import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.app.adapters.PinListAdapter;
import de.ur.mi.android.ting.model.IBoardsProvider;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.IPinProvider;
import de.ur.mi.android.ting.model.ISearchService;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.utilities.IConnectivity;
import de.ur.mi.android.ting.utilities.IImageLoader;
import de.ur.mi.android.ting.utilities.html.PinDataParser;

@Module(injects = { PinListAdapter.class, PinListController.class }, complete = false, library = true)
public class _ControllersModule {

	private CategoriesController categoryController;

	@Provides
	public SearchController provideSearchController(ISearchService searchService) {
		return new SearchController(searchService);
	}

	@Provides
	public CategoriesController provideCategoriesController(
			ICategoryProvider categoryProvider, IConnectivity connectivity) {
		if (this.categoryController == null) {
			this.categoryController = new CategoriesController(
					categoryProvider, connectivity);
		}
		return this.categoryController;
	}

	@Provides
	public ShareController provideShareController(PinDataParser pindataParser,
			IImageLoader imageLoader, IPinProvider pinProvider) {
		return new ShareController(pindataParser, imageLoader, pinProvider);
	}

	@Provides
	public UserBoardsController provideUserBoardsController(
			IBoardsProvider boardsProvider) {
		return new UserBoardsController(boardsProvider);
	}
	
	@Provides
	public LoginController provideLoginController(IUserService userService, IConnectivity connectivity){
		return new LoginController(userService, connectivity);
	}
}
