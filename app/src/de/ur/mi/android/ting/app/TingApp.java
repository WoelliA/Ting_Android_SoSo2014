package de.ur.mi.android.ting.app;

import android.app.Application;
import dagger.ObjectGraph;
import de.ur.mi.android.ting.app.activities._ActivityModule;
import de.ur.mi.android.ting.app.adapters._AdaptersModule;
import de.ur.mi.android.ting.app.controllers._ControllersModule;
import de.ur.mi.android.ting.app.fragments._FragmentsModule;
import de.ur.mi.android.ting.app.viewResolvers._ResolverModule;
import de.ur.mi.android.ting.model.dummy._DummyModelModule;
import de.ur.mi.android.ting.model.parse._ParseModelModule;
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
				new _ActivityModule(), new _FragmentsModule(),
				new UtilitiesModule(), new _AdaptersModule(), new AppModule(),
				new _ResolverModule(), new _ControllersModule() };
	}

	@Override
	public void inject(Object obj) {
		this.applicationGraph.inject(obj);
	}

	private Object getModelModule() {
		if (this.isReleaseApp) {
			return new _ParseModelModule(this);
		} else {
			return new _DummyModelModule();
		}
	}
}
