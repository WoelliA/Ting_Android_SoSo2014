package de.ur.mi.android.ting.app;

import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.model.IArticleProvider;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.dummy.DummyArticleProvider;
import de.ur.mi.android.ting.model.dummy.DummyCategoryProvider;

@Module(complete=true,
		library=true)
public class DummyModelIocModule implements IModelIocModule {
	@Provides
	public IArticleProvider provideIArticleProvider(){
		return new DummyArticleProvider();
	}

	@Provides
	public ICategoryProvider provideICategoryProvider() {
		return new DummyCategoryProvider();
	}
}
