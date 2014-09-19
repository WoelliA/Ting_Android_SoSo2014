package de.ur.mi.android.ting.app.activities;

import javax.inject.Singleton;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.app._AndroidModule;
import de.ur.mi.android.ting.utilities.view.INotify;
import de.ur.mi.android.ting.utilities.view.Notify;

@Module(injects = { 
		ShareActivity.class, 
		MainActivity.class,
		LoginActivity.class, 
		SearchActivity.class, 
		SplashScreenActivity.class,
		BoardDetailsActivity.class,
		UserDetailsActivity.class,
		EditBoardActivity.class,
		EditProfileActivity.class,
		SelectCategoriesActivity.class},
		

addsTo = _AndroidModule.class, library = true, complete = false)
public class _ActivityModule {

	private final Context context;
	private Notify notify;

	public _ActivityModule(Context context) {
		this.context = context;
		this.notify = new Notify(context);
	}

	@Provides
	@Singleton
	@ForActivity
	public Context provideActivityContext() {
		return this.context;
	}

	@Provides
	public INotify provideINotify() {
		return this.notify;
	}
}
