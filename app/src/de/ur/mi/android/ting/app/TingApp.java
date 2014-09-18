package de.ur.mi.android.ting.app;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Context;
import dagger.ObjectGraph;
import de.ur.mi.android.ting.app.adapters._AdaptersModule;
import de.ur.mi.android.ting.app.controllers._ControllersModule;
import de.ur.mi.android.ting.app.fragments._FragmentsModule;
import de.ur.mi.android.ting.app.viewResolvers._ResolverModule;
import de.ur.mi.android.ting.model.dummy._DummyModelModule;
import de.ur.mi.android.ting.model.parse._ParseModelModule;
import de.ur.mi.android.ting.utilities._UtilitiesModule;

public class TingApp extends Application implements IMainInjector {

	private static boolean isVisible;

	private boolean isReleaseApp = true;

	private ObjectGraph applicationGraph;

	private static Context context;
	
	private List<WeakReference<IChangeListener<Context>>> contextChangedListeners = new ArrayList<WeakReference<IChangeListener<Context>>>();

	private static TingApp current;

	public TingApp() {
		current = this;
	}
	
	public static TingApp current(){
		return current;
	}
	
	@Override
	public void onCreate() {
		Object[] modules = this.getModules();
		this.applicationGraph = ObjectGraph.create(modules);
		super.onCreate();
	}

	private Object[] getModules() {
		return new Object[] { 
				this.getModelModule(), 
				new _AndroidModule(this),
				new _FragmentsModule(), 
				new _UtilitiesModule(),
				new _AdaptersModule(), 
				new _AppModule(), 
				new _ResolverModule(),
				new _ControllersModule() };
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

	@Override
	public IInjector plus(Object... params) {
		Object[] extras = params;
		ObjectGraph graph = this.applicationGraph.plus(extras);
		return new GraphInjector(graph);
	}

	public static boolean isVisible() {
		return isVisible;
	}
	
	public static void isVisibleChanged(boolean visible){
		isVisible = visible;
	}

	public static void setActivityContext(Context context) {
		TingApp.context = context;		
		TingApp.current.notifyContextChangeListeners(context);
	}
	
	public static Context getActivityContext(){
		return TingApp.context;
	}
	
	private void notifyContextChangeListeners(Context context){
		for (WeakReference<IChangeListener<Context>> weakReference : this.contextChangedListeners) {
			IChangeListener<Context> listener = weakReference.get();
			if(listener != null){
				listener.onChange(context);
			}
		}
	}

	public void addContextChangedListener(IChangeListener<Context> listener) {
		this.contextChangedListeners.add(new WeakReference<IChangeListener<Context>>(listener));
	}
}
