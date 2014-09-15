package de.ur.mi.android.ting.model;

import android.content.Context;


public interface _IModelModule {
	public IPinService provideIPinService(LocalUser user);
	public ICategoryProvider provideICategoryProvider(LocalUser user, IUserService userService);
	public IBoardsService provideIBoardsProvider(LocalUser user);
	public IUserService provideIUserService(LocalUser user);
	public LocalUser provideLocalUser();
	ISpecialCategories provideISpecialCategories(Context context);
}
