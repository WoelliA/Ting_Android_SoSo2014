package de.ur.mi.android.ting.app.activities;

import android.app.Activity;
import android.os.Bundle;
import de.ur.mi.android.ting.app.IInjector;
import de.ur.mi.android.ting.app.IMainInjector;
import de.ur.mi.android.ting.app.controllers._ControllersModule;

public abstract class BaseActivity extends Activity implements IInjector {
	private IInjector activityInjector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		IMainInjector mainInjector = (IMainInjector) this.getApplication();

	    activityInjector = mainInjector.plus(getModules());
	    activityInjector.inject(this);
	}
	
	private Object[] getModules() {
	    return new Object[] {new _ActivityModule(this) };
	}

	@Override
	public void inject(Object obj) {
	    activityInjector.inject(obj);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
