package de.ur.mi.android.ting.app.activities.tutorial;

import android.os.Bundle;
import android.view.Window;
import de.ur.mi.android.ting.app.Tutorial;
import de.ur.mi.android.ting.app.activities.EditProfileActivity;

public class EditProfileTutorialActivity extends EditProfileActivity {

	private Tutorial tutorial;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.tutorial = Tutorial.getTutorial(this.getIntent());
	}
	
	@Override
	protected void onSaved() {
		super.onSaved();
		tutorial.proceed(this);
	}
	
}
