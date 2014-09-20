package de.ur.mi.android.ting.app.activities.tutorial;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.Tutorial;
import de.ur.mi.android.ting.app.activities.EditBoardActivity;
import de.ur.mi.android.ting.app.controllers.BoardEditRequest;

public class CreateBoardTutorialActivity extends EditBoardActivity {

	private Tutorial tutorial;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.tutorial = Tutorial.getTutorial(this.getIntent());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(R.string.action_skip);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle().equals(getString(R.string.action_skip))){
			this.tutorial.proceed(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBoardSaved(BoardEditRequest request) {
		if (this.tutorial != null) {
			this.tutorial.proceed(this);
		}
		super.onBoardSaved(request);
	}

}
