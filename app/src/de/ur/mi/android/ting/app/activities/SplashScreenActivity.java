package de.ur.mi.android.ting.app.activities;

import javax.inject.Inject;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.utilities.IAppStart;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.initialization.IInitializeable;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class SplashScreenActivity extends BaseActivity {

	@Inject
	public IInitializeable initializeable;

	@Inject
	public IAppStart appStart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_splashscreen);
		
		this.initializeable.initialize(new IDoneCallback<Void>() {

			@Override
			public void done(Void result) {
				proceed();
			}
		});
	}

	protected void proceed() {
		Intent intent = new Intent(this, appStart.getStartActivityClass());
		this.startActivity(intent);
	}
}
