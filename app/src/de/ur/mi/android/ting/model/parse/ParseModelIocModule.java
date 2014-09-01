package de.ur.mi.android.ting.model.parse;

import javax.inject.Singleton;

import android.content.Context;

import com.parse.Parse;

import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.model.IBoardsProvider;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.IModelIocModule;
import de.ur.mi.android.ting.model.IPinProvider;
import de.ur.mi.android.ting.model.ISearchService;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;

@Module(complete = true, library = true)
public class ParseModelIocModule implements IModelIocModule {

	private static final String applicationId = "rnklQPqG2yqcKwmXfYqMSqQ2CoF6lGB56sofWiHt";
	private static final String clientKey = "u6OGwXAEEcm1qUZ8n75o5SuDBLo3rLw8kZrsAxCp";

	public ParseModelIocModule(Context context) {
		// Parse.enableLocalDatastore(context);
		Parse.initialize(context, applicationId, clientKey);
	}

	@Override
	@Provides
	public IPinProvider provideIPinProvider() {
		return new ParsePinProvider();
	}

	@Override
	@Provides
	@Singleton
	public ICategoryProvider provideICategoryProvider(LocalUser user) {
		return new ParseCategoryProvider(user);
	}

	@Override
	@Provides
	public IBoardsProvider provideIBoardsProvider(LocalUser user) {
		return new ParseBoardsProvider(user);
	}

	@Override
	@Provides
	public IUserService provideIUserService(LocalUser user, ICategoryProvider categoryProvider) {
		return new ParseUserService(user);
	}

	@Override
	@Provides
	public ISearchService provideISearchService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Provides
	@Singleton
	public LocalUser provideLocalUser() {

		return new LocalUser();
	}

}
