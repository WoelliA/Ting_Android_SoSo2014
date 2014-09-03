package de.ur.mi.android.ting.app.activities;

import javax.inject.Inject;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.utilities.IAppStart;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;
import de.ur.mi.android.ting.utilities.initialization.IInitializeable;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreenActivity extends BaseActivity {

	@Inject
	public IInitializeable initializeable;

	@Inject
	public IAppStart appStart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.activity_splashscreen);
		
		this.initializeable.initialize(new SimpleDoneCallback<Void>() {

			@Override
			public void done(Void result) {
				SplashScreenActivity.this.proceed();
			}
		});
	}

	protected void proceed() {
		Intent intent = new Intent(this, this.appStart.getStartActivityClass());
		this.startActivity(intent);
	}
}
