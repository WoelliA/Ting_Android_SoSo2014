package de.ur.mi.android.ting.app.activities;

import de.ur.mi.android.ting.app.IInjector;
import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity implements IInjector {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		((IInjector) this.getApplication()).inject(this);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void inject(Object obj) {
		((IInjector) this.getApplication()).inject(this);		
	}
}
