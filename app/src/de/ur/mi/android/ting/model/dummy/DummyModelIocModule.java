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
	@Singleton
	public IUser provideIUser() {
		return new LocalUser();
	}

	@Override
	@Provides
	public ISearchService provideISearchService() {
		return new DummySearchService();
	}

	@Override
	@Provides
	public IPinProvider provideIPinProvider(IUser user) {

		return new DummyPinProvider();
	}

	@Override
	@Provides
	public IBoardsProvider provideIBoardsProvider(IUser user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Provides
	public IUserService provideIUserService(IUser user) {
		return new DummyUserService(user);
	}
}
