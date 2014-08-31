package de.ur.mi.android.ting.model.dummy;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.model.IBoardsProvider;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.IModelIocModule;
import de.ur.mi.android.ting.model.IPinProvider;
import de.ur.mi.android.ting.model.ISearchService;
import de.ur.mi.android.ting.model.IUser;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;

@Module(complete=true,
		library=true)
public class DummyModelIocModule implements IModelIocModule {
	
	@Override
	@Provides
	public ICategoryProvider provideICategoryProvider() {
		return new DummyCategoryProvider();
	}

	@Override
	@Provides
	public IPinProvider provideIPinProvider() {
		return new DummyPinProvider();
	}

	@Override
	@Provides
	public IBoardsProvider provideIBoardsProvider() {
		return null;
	}

	@Override
	@Provides
	public IUserService provideIUserService() {
		return new DummyUserService(this.provideIUser());
	}

	@Override
	@Provides
	@Singleton
	public IUser provideIUser() {
		return new LocalUser();
	}

	@Override
	@Provides
	public ISearchService provideISearchService() {
		return new DummySearchService();
	}
}
