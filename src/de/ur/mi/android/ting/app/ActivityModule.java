package de.ur.mi.android.ting.app;

import dagger.Module;
import de.ur.mi.android.ting.activities.LoginActivity;
import de.ur.mi.android.ting.activities.MainActivity;

@Module(injects = { 
		MainActivity.class, 		
		LoginActivity.class
		
}, 
		complete = false)
public class ActivityModule {

}
