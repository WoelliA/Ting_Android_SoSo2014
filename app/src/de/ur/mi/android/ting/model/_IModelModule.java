package de.ur.mi.android.ting.model;


public interface _IModelModule {
	public IPinService provideIPinService();
	public ICategoryProvider provideICategoryProvider(LocalUser user);
	public IBoardsService provideIBoardsProvider(LocalUser user);
	public IUserService provideIUserService(LocalUser user, ICategoryProvider categoryProvider);
	public LocalUser provideLocalUser();
	public ISearchService provideISearchService();
}
