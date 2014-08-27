package de.ur.mi.android.ting.app;

import dagger.Module;
import de.ur.mi.android.ting.app.fragments.NavigationDrawerFragment;
import de.ur.mi.android.ting.app.fragments.PinListFragment;

@Module(injects = { 
		NavigationDrawerFragment.class, 		
		PinListFragment.class,		
}, 
		complete = false)
public class FragmentsModule {

}
