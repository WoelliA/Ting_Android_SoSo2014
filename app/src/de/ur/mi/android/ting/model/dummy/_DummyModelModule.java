package de.ur.mi.android.ting.model.dummy;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.model.IBoardsProvider;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model._IModelModule;
import de.ur.mi.android.ting.model.IPinProvider;
import de.ur.mi.android.ting.model.ISearchService;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;

@Module(complete = true, library = true)
public class _DummyModelModule implements _IModelModule {

	@Override
	@Provides
	@Singleton
	public ICategoryProvider provideICategoryProvider(LocalUser user) {
		return new DummyCategoryProvider(user);
	}

	@Override
	@Provides
	@Singleton
	public LocalUser provideLocalUser() {
		return new LocalUser();
	}

	@Override
	@Provides
	public ISearchService provideISearchService() {
		return new DummySearchService();
	}

	@Override
	@Provides
	public IPinProvider provideIPinProvider() {
		return new DummyPinProvider();
	}

	@Override
	@Provides
	public IBoardsProvider provideIBoardsProvider(LocalUser user) {
		return null;
	}

	@Override
	@Provides
	public IUserService provideIUserService(LocalUser user,
			ICategoryProvider categoryProvider) {
		return new DummyUserService(user, categoryProvider);
	}
}
