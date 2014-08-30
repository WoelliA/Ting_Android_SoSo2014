package de.ur.mi.android.ting.model;


public interface IModelIocModule {
	public IPinProvider provideIPinProvider( );
	public ICategoryProvider provideICategoryProvider();
	public IBoardsProvider provideIBoardsProvider();
	public IUserService provideIUserService();
}
