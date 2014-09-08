package de.ur.mi.android.ting.utilities;

import javax.inject.Singleton;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.app.ForApplication;
import de.ur.mi.android.ting.app.activities.ForActivity;
import de.ur.mi.android.ting.utilities.html.JSoupPinDataParser;
import de.ur.mi.android.ting.utilities.html.PinDataParser;
import de.ur.mi.android.ting.utilities.view.INotify;
import de.ur.mi.android.ting.utilities.view.Notify;

@Module(
		complete = false, 
		library = true)
public class _UtilitiesModule {
	@Provides
	@Singleton
	public IImageLoader provideIImageLoader(@ForApplication Context context) {
		return new DelegatingImageLoader(context);
	}
	
	@Provides
	public PinDataParser providePinDataParser(IImageLoader imageLoader){
		return new JSoupPinDataParser(imageLoader);
	}
	
	@Provides
	@Singleton
	public IConnectivity provideIConnectivity(@ForApplication Context context) {
		 Connectivity connectivity = new Connectivity(context);
		 return connectivity;
	}

}
