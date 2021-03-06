package de.ur.mi.android.ting.model.parse;

import java.util.HashMap;

import javax.inject.Singleton;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;

import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.app.ForApplication;
import de.ur.mi.android.ting.model.IBoardsService;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.ISearchService;
import de.ur.mi.android.ting.model.ISocialService;
import de.ur.mi.android.ting.model.ISpecialCategories;
import de.ur.mi.android.ting.model.SpecialCategories;
import de.ur.mi.android.ting.model._IModelModule;
import de.ur.mi.android.ting.model.IPinService;
import de.ur.mi.android.ting.model.ITypedSearchService;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.SearchType;

@Module(injects = { ParseSocialService.class }, complete = false, library = true)
public class _ParseModelModule implements _IModelModule {

	private static final String applicationId = "rnklQPqG2yqcKwmXfYqMSqQ2CoF6lGB56sofWiHt";
	private static final String clientKey = "u6OGwXAEEcm1qUZ8n75o5SuDBLo3rLw8kZrsAxCp";
	private ICategoryProvider categoryProvider;
	private LocalUser localUser;

	public _ParseModelModule(Context context) {
		// Parse.enableLocalDatastore(context);
		Parse.initialize(context, applicationId, clientKey);
		ParseFacebookUtils.initialize("235547439794245");
		ParseTwitterUtils.initialize("7lqEeDTEdYEW2U7mO7Psyg",
				"lE1SB6OjqBWyOzYFl0fMIhJxElZWNRDaNArjZeck");
	}

	@Override
	@Provides
	public IPinService provideIPinService(LocalUser user) {
		return new ParsePinService(user);
	}

	@Override
	@Provides
	@Singleton
	public ICategoryProvider provideICategoryProvider(LocalUser user,
			IUserService userService) {
		if (this.categoryProvider == null) {
			this.categoryProvider = new ParseCategoryProvider(user, userService);
		}
		return this.categoryProvider;
	}

	@Override
	@Provides
	public IBoardsService provideIBoardsProvider(LocalUser user) {
		return new ParseBoardsService(user);
	}

	@Override
	@Provides
	public IUserService provideIUserService(LocalUser user) {
		return new ParseUserService(user);
	}

	@Provides
	public ISearchService provideISearchService(IUserService userService,
			IPinService pinService, IBoardsService boardsService) {

		HashMap<SearchType, ITypedSearchService<?>> services = new HashMap<SearchType, ITypedSearchService<?>>();
		services.put(SearchType.USER, userService);
		services.put(SearchType.BOARD, boardsService);
		services.put(SearchType.PIN, pinService);
		return new ParseSearchProvider(services);
	}

	@Override
	@Provides
	@Singleton
	public LocalUser provideLocalUser() {
		if (this.localUser == null) {
			this.localUser = new LocalUser();
		}
		return this.localUser;
	}

	@Override
	@Provides
	public ISpecialCategories provideISpecialCategories(
			@ForApplication Context context) {
		return new SpecialCategories(context);
	}

	@Override
	public ISocialService provideSocialService() {
		return new ParseSocialService();
	}

}
