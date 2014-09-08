package de.ur.mi.android.ting.app.activities;

import javax.inject.Singleton;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.app.ForApplication;
import de.ur.mi.android.ting.app._AndroidModule;
import de.ur.mi.android.ting.utilities.Connectivity;
import de.ur.mi.android.ting.utilities.IConnectivity;
import de.ur.mi.android.ting.utilities.view.INotify;
import de.ur.mi.android.ting.utilities.view.Notify;

@Module(injects = { ShareActivity.class, MainActivity.class,
		LoginActivity.class, SearchActivity.class, SplashScreenActivity.class },

addsTo = _AndroidModule.class, library = true, complete = false)
public class _ActivityModule {

	private final Context context;
	private Notify notify;

	public _ActivityModule(Context context) {
		this.context = context;

		notify = new Notify(context);
	}

	@Provides
	@Singleton
	@ForActivity
	public Context provideActivityContext() {
		return context;
	}

	@Provides
	public INotify provideINotify() {
		return notify;
	}
}
