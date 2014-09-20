package de.ur.mi.android.ting.app.activities.tutorial;

import javax.inject.Inject;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.Tutorial;
import de.ur.mi.android.ting.app.activities.BrowseBoardsActivity;
import de.ur.mi.android.ting.app.controllers.MultiSelectCategoriesController;

public class BrowseBoardsTutorialActivity extends BrowseBoardsActivity {
	private Tutorial tutorial;

	@Inject
	MultiSelectCategoriesController categoriesController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.tutorial = Tutorial.getTutorial(getIntent());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.save, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_save:
			categoriesController.saveFavoriteCategories(categoriesController
					.getSelectedCategories());
			tutorial.proceed(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
