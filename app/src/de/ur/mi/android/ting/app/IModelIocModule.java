package de.ur.mi.android.ting.app;

import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.IPinProvider;

public interface IModelIocModule {
	public IPinProvider provideIArticleProvider();
	public ICategoryProvider provideICategoryProvider();
}
