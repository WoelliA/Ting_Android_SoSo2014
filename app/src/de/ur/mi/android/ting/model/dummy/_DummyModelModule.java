package de.ur.mi.android.ting.model.dummy;

import javax.inject.Singleton;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.app.ForApplication;
import de.ur.mi.android.ting.model.IBoardsService;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.ISearchService;
import de.ur.mi.android.ting.model.ISpecialCategories;
import de.ur.mi.android.ting.model.SpecialCategories;
import de.ur.mi.android.ting.model._IModelModule;
import de.ur.mi.android.ting.model.IPinService;
import de.ur.mi.android.ting.model.ITypedSearchService;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;

@Module(complete = false, library = true)
public class _DummyModelModule implements _IModelModule {

	private DummyCategoryProvider categoryProvider;

	@Override
	@Provides
	@Singleton
	public ICategoryProvider provideICategoryProvider(LocalUser user, ISpecialCategories specialCategories) {
		if(this.categoryProvider == null) {
			this.categoryProvider = new DummyCategoryProvider(user, specialCategories);
		}
		return this.categoryProvider;
	}

	@Override
	@Provides
	@Singleton
	public LocalUser provideLocalUser() {
		return new LocalUser();
	}

	@Provides
	public ISearchService provideISearchService() {
		return new DummySearchService();
	}

	@Override
	@Provides
	public IPinService provideIPinService(LocalUser user) {
		return new DummyPinProvider();
	}

	@Override
	@Provides
	public IBoardsService provideIBoardsProvider(LocalUser user) {
		return new DummyBoardsProvider();
	}

	@Override
	@Provides
	public IUserService provideIUserService(LocalUser user,
			ICategoryProvider categoryProvider) {
		return new DummyUserService(user, categoryProvider);
	}

	@Override
	@Provides
	public ISpecialCategories provideISpecialCategories(@ForApplication Context context) {
		return new SpecialCategories(context);
	}
}
