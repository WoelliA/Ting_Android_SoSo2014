package de.ur.mi.android.ting.app.activities;

import de.ur.mi.android.ting.app.IInjector;
import android.support.v7.app.ActionBarActivity;

public class ActionBarActivityBase extends ActionBarActivity implements IInjector {

	@Override
	public void inject(Object obj) {
		((IInjector)this.getApplication()).inject(obj);
		
	}

}
