package de.ur.mi.android.ting.app;

import dagger.Module;
import de.ur.mi.android.ting.app.activities.LoginActivity;
import de.ur.mi.android.ting.app.activities.MainActivity;

@Module(injects = { 
		MainActivity.class, 		
		LoginActivity.class,		
}, 
		complete = false)
public class ActivityModule {

}
