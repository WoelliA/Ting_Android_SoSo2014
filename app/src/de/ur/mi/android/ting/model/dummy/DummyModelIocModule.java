package de.ur.mi.android.ting.model.dummy;

import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.model.IBoardsProvider;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.IModelIocModule;
import de.ur.mi.android.ting.model.IPinProvider;
import de.ur.mi.android.ting.model.IUserService;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IUserService provideIUserService() {
		// TODO Auto-generated method stub
		return null;
	}
}
