package de.ur.mi.android.ting.app.activities;

import dagger.Module;

@Module(injects = { 
		ShareActivity.class,
		MainActivity.class, 		
		LoginActivity.class,
		SearchActivity.class,
		SplashScreenActivity.class
		
}, 
		complete = false)
public class _ActivityModule {

}
