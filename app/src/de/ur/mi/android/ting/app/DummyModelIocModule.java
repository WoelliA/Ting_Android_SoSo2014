package de.ur.mi.android.ting.app;

import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.IPinProvider;
import de.ur.mi.android.ting.model.dummy.DummyPinProvider;
import de.ur.mi.android.ting.model.dummy.DummyCategoryProvider;

@Module(complete=true,
		library=true)
public class DummyModelIocModule implements IModelIocModule {
	@Override
	@Provides
	public IPinProvider provideIArticleProvider(){
		return new DummyPinProvider();
	}

	@Override
	@Provides
	public ICategoryProvider provideICategoryProvider() {
		return new DummyCategoryProvider();
	}
}
