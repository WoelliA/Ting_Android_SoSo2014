package de.ur.mi.android.ting.app.activities;

import de.ur.mi.android.ting.app.IInjector;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class ActionBarActivityBase extends ActionBarActivity implements IInjector {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.inject(this);
	}
	
	@Override
	public void inject(Object obj) {
		((IInjector)this.getApplication()).inject(obj);
		
	}

}
