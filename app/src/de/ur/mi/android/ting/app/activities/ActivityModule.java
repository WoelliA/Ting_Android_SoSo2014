package de.ur.mi.android.ting.app.activities;

import dagger.Module;

@Module(injects = { 
		MainActivity.class, 		
		LoginActivity.class,
		SplashScreenActivity.class
		
}, 
		complete = false)
public class ActivityModule {

}
