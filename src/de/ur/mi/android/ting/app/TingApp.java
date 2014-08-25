package de.ur.mi.android.ting.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import android.app.Application;

public class TingApp extends Application implements IInjector {

	private ObjectGraph applicationGraph;

	@Override
	public void onCreate() {
		Object[] modules = this.getModules();
		this.applicationGraph = ObjectGraph.create(modules);
		super.onCreate();
	}

	private Object[] getModules() {
		return new Object[]{
			getModelModule(), new ActivityModule()
		};
	}

	public void inject(Object obj) {
		this.applicationGraph.inject(obj);
	}

	private Object getModelModule() {
		return new DummyModelIocModule();
		
		// TODO: at production replace with:
		// return new ProductionModelIocModule();
	}
}
