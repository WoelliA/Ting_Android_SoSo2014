package de.ur.mi.android.ting.activities;

import de.ur.mi.android.ting.app.IInjector;
import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		((IInjector) this.getApplication()).inject(this);
		super.onCreate(savedInstanceState);
	}
}
