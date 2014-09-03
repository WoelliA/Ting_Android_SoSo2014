package de.ur.mi.android.ting.app;

import android.app.Application;
import dagger.ObjectGraph;
import de.ur.mi.android.ting.app.activities.ActivityModule;
import de.ur.mi.android.ting.app.adapters.AdaptersModule;
import de.ur.mi.android.ting.app.controllers.ControllersModule;
import de.ur.mi.android.ting.app.fragments.FragmentsModule;
import de.ur.mi.android.ting.app.viewResolvers.ResolverModule;
import de.ur.mi.android.ting.model.dummy.DummyModelIocModule;
import de.ur.mi.android.ting.model.parse.ParseModelIocModule;
import de.ur.mi.android.ting.utilities.UtilitiesModule;

public class TingApp extends Application implements IInjector {

	private boolean isReleaseApp = false;

	private ObjectGraph applicationGraph;

	@Override
	public void onCreate() {
		Object[] modules = this.getModules();
		this.applicationGraph = ObjectGraph.create(modules);
		super.onCreate();
	}

	private Object[] getModules() {
		return new Object[] { this.getModelModule(), new AndroidModule(this),
				new ActivityModule(), new FragmentsModule(),
				new UtilitiesModule(), new AdaptersModule(), new AppModule(),
				new ResolverModule(), new ControllersModule() };
	}

	@Override
	public void inject(Object obj) {
		this.applicationGraph.inject(obj);
	}

	private Object getModelModule() {
		if (this.isReleaseApp) {
			return new ParseModelIocModule(this);
		} else {
			return new DummyModelIocModule();
		}
	}
}
