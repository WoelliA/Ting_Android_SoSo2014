package de.ur.mi.android.ting.app;

import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.model.IArticleProvider;
import de.ur.mi.android.ting.model.ICategoryProvider;

@Module(complete=true,
library=true)
public class ProductionModelIocModule implements IModelIocModule {

	@Provides
	public IArticleProvider provideIArticleProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Provides
	public ICategoryProvider provideICategoryProvider() {
		// TODO Auto-generated method stub
		return null;
	}

}
