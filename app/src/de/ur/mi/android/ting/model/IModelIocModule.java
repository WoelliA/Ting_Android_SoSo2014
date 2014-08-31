package de.ur.mi.android.ting.model;


public interface IModelIocModule {
	public IPinProvider provideIPinProvider(IUser user);
	public ICategoryProvider provideICategoryProvider();
	public IBoardsProvider provideIBoardsProvider(IUser user);
	public IUserService provideIUserService(IUser user);
	public IUser provideIUser();
	public ISearchService provideISearchService();
}
