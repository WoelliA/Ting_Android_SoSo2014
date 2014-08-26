package de.ur.mi.android.ting.app;

import dagger.ObjectGraph;
import android.app.Application;

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
		return new Object[] { getModelModule(), new ActivityModule() };
	}

	@Override
	public void inject(Object obj) {
		this.applicationGraph.inject(obj);
	}

	private Object getModelModule() {
		if (isReleaseApp)
			return new ProductionModelIocModule();
		else
			return new DummyModelIocModule();
	}
}
