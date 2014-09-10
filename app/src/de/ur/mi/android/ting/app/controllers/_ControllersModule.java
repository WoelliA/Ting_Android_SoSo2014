package de.ur.mi.android.ting.app.controllers;

import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.app.adapters.PinListAdapter;
import de.ur.mi.android.ting.model.IBoardsService;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.IPinService;
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
			IImageLoader imageLoader, IPinService pinService) {
		return new ShareController(pindataParser, imageLoader, pinService);
	}

	@Provides
	public UserBoardsController provideUserBoardsController(
			IBoardsService boardsService) {
		return new UserBoardsController(boardsService);
	}
	
	@Provides
	public LoginController provideLoginController(IUserService userService, IConnectivity connectivity){
		return new LoginController(userService, connectivity);
	}
	
	@Provides
	public BoardDetailsController provideBoardDetailsController(IPinService pinService, IBoardsService boardsService){
		return new BoardDetailsController(pinService, boardsService);
	}
	
	@Provides 
	public UserDetailsController provideUserDetailsController(IUserService userService, IBoardsService boardsService){
		return new UserDetailsController(userService, boardsService);
	}
}
