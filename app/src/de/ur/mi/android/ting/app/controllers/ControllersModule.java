package de.ur.mi.android.ting.app.controllers;

import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.app.adapters.PinListAdapter;

@Module(injects = { PinListAdapter.class }, complete = false, library = true)
public class ControllersModule {

	@Provides
	public SearchController provideSearchController() {
		return new SearchController();
	}
}
