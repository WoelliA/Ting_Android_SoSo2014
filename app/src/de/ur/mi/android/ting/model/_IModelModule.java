package de.ur.mi.android.ting.model;

import android.content.Context;


public interface _IModelModule {
	public IPinService provideIPinService(LocalUser user);
	public ICategoryProvider provideICategoryProvider(LocalUser user, ISpecialCategories specialCategories);
	public IBoardsService provideIBoardsProvider(LocalUser user);
	public IUserService provideIUserService(LocalUser user, ICategoryProvider categoryProvider);
	public LocalUser provideLocalUser();
	ISpecialCategories provideISpecialCategories(Context context);
}
