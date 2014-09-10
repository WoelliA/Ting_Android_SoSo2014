package de.ur.mi.android.ting.model.parse;

import javax.inject.Singleton;

import android.content.Context;

import com.parse.Parse;

import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.app.ForApplication;
import de.ur.mi.android.ting.model.IBoardsService;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.ISpecialCategories;
import de.ur.mi.android.ting.model.SpecialCategories;
import de.ur.mi.android.ting.model._IModelModule;
import de.ur.mi.android.ting.model.IPinService;
import de.ur.mi.android.ting.model.ISearchService;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;

@Module(complete = false, library = true)
public class _ParseModelModule implements _IModelModule {

	private static final String applicationId = "rnklQPqG2yqcKwmXfYqMSqQ2CoF6lGB56sofWiHt";
	private static final String clientKey = "u6OGwXAEEcm1qUZ8n75o5SuDBLo3rLw8kZrsAxCp";
	private ICategoryProvider categoryProvider;

	public _ParseModelModule(Context context) {
		// Parse.enableLocalDatastore(context);
		Parse.initialize(context, applicationId, clientKey);
	}

	@Override
	@Provides
	public IPinService provideIPinService(LocalUser user) {
		return new ParsePinService(user);
	}

	@Override
	@Provides
	@Singleton
	public ICategoryProvider provideICategoryProvider(LocalUser user, ISpecialCategories specialCategories) {
		if(this.categoryProvider == null) {
			this.categoryProvider = new ParseCategoryProvider(user, specialCategories);
		}
		return this.categoryProvider;
	}

	@Override
	@Provides
	public IBoardsService provideIBoardsProvider(LocalUser user) {
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
	
	
	@Override
	@Provides
	public ISpecialCategories provideISpecialCategories(@ForApplication Context context) {
		return new SpecialCategories(context);
	}

}
