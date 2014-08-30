package de.ur.mi.android.ting.model.parse;

import javax.inject.Inject;
import javax.inject.Singleton;

import android.content.Context;

import com.parse.Parse;

import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.model.IBoardsProvider;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.IModelIocModule;
import de.ur.mi.android.ting.model.IPinProvider;
import de.ur.mi.android.ting.model.IUserService;

@Module(complete = true, library = true)
public class ParseModelIocModule implements IModelIocModule {

	private static final String applicationId = "rnklQPqG2yqcKwmXfYqMSqQ2CoF6lGB56sofWiHt";
	private static final String clientKey = "u6OGwXAEEcm1qUZ8n75o5SuDBLo3rLw8kZrsAxCp";

	public ParseModelIocModule(Context context) {
		// Parse.enableLocalDatastore(context);
		Parse.initialize(context, applicationId, clientKey);
	}

	@Provides
	public IPinProvider provideIPinProvider() {
		return new ParsePinProvider(
				(ParseBoardsProvider) this.provideIBoardsProvider());
	}

	@Provides
	@Singleton
	public ICategoryProvider provideICategoryProvider() {
		return new ParseCategoryProvider();
	}

	@Provides
	public IBoardsProvider provideIBoardsProvider() {
		return new ParseBoardsProvider(
				(ParseCategoryProvider) this.provideICategoryProvider(),
				(ParseUserService) this.provideIUserService());
	}

	@Provides
	public IUserService provideIUserService() {
		return new ParseUserService();
	}

}
