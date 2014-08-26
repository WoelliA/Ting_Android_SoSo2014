package de.ur.mi.android.ting.app;

import de.ur.mi.android.ting.model.IArticleProvider;
import de.ur.mi.android.ting.model.ICategoryProvider;

public interface IModelIocModule {
	public IArticleProvider provideIArticleProvider();
	public ICategoryProvider provideICategoryProvider();
}
