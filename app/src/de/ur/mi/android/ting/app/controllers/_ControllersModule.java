package de.ur.mi.android.ting.app.controllers;

import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.app.adapters.PinListAdapter;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.IPinService;
import de.ur.mi.android.ting.model.ISearchService;
import de.ur.mi.android.ting.model.ISpecialCategories;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.utilities.IConnectivity;
import de.ur.mi.android.ting.utilities.IImageLoader;
import de.ur.mi.android.ting.utilities.html.PinDataParser;

@Module(injects = { PinListAdapter.class, PinListController.class }, complete = false, library = true)
public class _ControllersModule {

	private MainCategoriesController categoryController;

	@Provides
	public SearchController provideSearchController(
			ISearchService typedSearchService) {
		return new SearchController(typedSearchService);
	}

	@Provides
	public LoginController provideLoginController(IUserService userService,
			IConnectivity connectivity, LocalUser user) {
		return new LoginController(userService, connectivity, user);
	}

	
	@Provides
	public EditProfileController provideEditProfileController(IImageLoader imageloader, IUserService userService, LocalUser user){
		return new EditProfileController(imageloader, userService, user);
	}
}
