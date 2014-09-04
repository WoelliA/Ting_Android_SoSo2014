package de.ur.mi.android.ting.model;


public interface _IModelModule {
	public IPinProvider provideIPinProvider();
	public ICategoryProvider provideICategoryProvider(LocalUser user);
	public IBoardsProvider provideIBoardsProvider(LocalUser user);
	public IUserService provideIUserService(LocalUser user, ICategoryProvider categoryProvider);
	public LocalUser provideLocalUser();
	public ISearchService provideISearchService();
}
