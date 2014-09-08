package de.ur.mi.android.ting.app.activities;

import de.ur.mi.android.ting.app.IInjector;
import de.ur.mi.android.ting.app.IMainInjector;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class FragmentActivityBase extends FragmentActivity implements IInjector{
	private IInjector activityInjector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		IMainInjector mainInjector = (IMainInjector) this.getApplication();

	    this.activityInjector = mainInjector.plus(this.getModules());
	    this.activityInjector.inject(this);
	}
	
	private Object[] getModules() {
	    return new Object[] {new _ActivityModule(this) };
	}

	@Override
	public void inject(Object obj) {
	    this.activityInjector.inject(obj);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
