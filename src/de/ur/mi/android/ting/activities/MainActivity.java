package de.ur.mi.android.ting.activities;

import java.util.List;

import javax.inject.Inject;

import de.ur.mi.android.ting.model.IArticleProvider;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.ICategoryReceivedCallback;
import de.ur.mi.android.ting.model.Primitives.Category;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends BaseActivity implements ICategoryReceivedCallback {

	@Inject
	public IArticleProvider articleProvider;

	@Inject
	public ICategoryProvider categoryProvider;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.categoryProvider.GetAllCategories(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onCategoriesReceived(List<Category> categories) {
		
	}
}
