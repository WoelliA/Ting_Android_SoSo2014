package de.ur.mi.android.ting.app.controllers;

import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.app.adapters.PinListAdapter;
import de.ur.mi.android.ting.model.IBoardsService;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.IPinService;
import de.ur.mi.android.ting.model.ISearchService;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.utilities.IConnectivity;
import de.ur.mi.android.ting.utilities.IImageLoader;
import de.ur.mi.android.ting.utilities.html.PinDataParser;

@Module(injects = { PinListAdapter.class, PinListController.class }, complete = false, library = true)
public class _ControllersModule {

	private CategoriesController categoryController;

	@Provides
	public SearchController provideSearchController(
			ISearchService typedSearchService) {
		return new SearchController(typedSearchService);
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
	public LoginController provideLoginController(IUserService userService,
			IConnectivity connectivity, LocalUser user) {
		return new LoginController(userService, connectivity, user);
	}

	@Provides
	public UserDetailsController provideUserDetailsController(
			IUserService userService, IBoardsService boardsService) {
		return new UserDetailsController(userService, boardsService);
	}

	@Provides
	public EditBoardController provideEditBoardController(
			IBoardsService boardsService, IConnectivity conn,
			ICategoryProvider cateProv) {
		return new EditBoardController(boardsService, cateProv, conn);
	}
	
	@Provides
	public EditProfileController provideEditProfileController(IImageLoader imageloader, IUserService userService, LocalUser user){
		return new EditProfileController(imageloader, userService, user);
	}
}
