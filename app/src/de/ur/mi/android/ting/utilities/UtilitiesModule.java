package de.ur.mi.android.ting.utilities;

import javax.inject.Singleton;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.app.ForApplication;

@Module(complete = false, library = true)
public class UtilitiesModule {
	@Provides
	@Singleton
	public IImageLoader provideIImageLoader(@ForApplication Context context) {
		return new DelegatingImageLoader(context);
	}
}
