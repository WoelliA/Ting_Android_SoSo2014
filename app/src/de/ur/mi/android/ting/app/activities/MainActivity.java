package de.ur.mi.android.ting.app.activities;

import javax.inject.Inject;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.app.ISelectedListener;
import de.ur.mi.android.ting.app.controllers.CategoriesController;
import de.ur.mi.android.ting.app.controllers.LoginController;
import de.ur.mi.android.ting.app.controllers.MainCategoriesController;
import de.ur.mi.android.ting.app.controllers.PinListController;
import de.ur.mi.android.ting.app.fragments.PinListFragment;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.LoginResult;

public class MainActivity extends DrawerActivityBase implements
		ISelectedListener<Category> {

	private Menu menu;
	private SearchView searchView;
	protected String firstTitle;
	PinListFragment pinContent;

	@Inject
	public LocalUser user;

	@Inject
	public LoginController loginController;

	@Inject
	public MainCategoriesController categoryController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		this.categoryController.setSelectedCategoryChangeListener(this);
		if (this.categoryController.getSelectedCategory() != null) {
			this.setCategory(this.categoryController.getSelectedCategory());
		}

		this.getSupportFragmentManager().addOnBackStackChangedListener(
				new OnBackStackChangedListener() {
					@Override
					public void onBackStackChanged() {
						FragmentManager manager = MainActivity.this
								.getSupportFragmentManager();
						int backstackCount = manager.getBackStackEntryCount();
						String name;
						if (backstackCount > 0) {
							name = manager.getBackStackEntryAt(
									backstackCount - 1).getName();
						} else {
							name = MainActivity.this.firstTitle;
						}
						MainActivity.this.setTitle(name);
					}
				});

		this.categoryController.setSelectedCategoryChangeListener(this);
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
		this.user.addLoginChangeListener(new IChangeListener<LoginResult>() {

			@Override
			public void onChange(LoginResult changed) {
				MainActivity.this.adjustOptionsMenu();
			}
		});
		this.adjustOptionsMenu();
		if (this.getCurrentFocus() != null) {
			this.getCurrentFocus().clearFocus();
		}

		if (this.searchView != null) {
			this.searchView.setIconified(true);
		}
	}

	private void setCategory(Category selectedCategory) {
		this.setTitle(selectedCategory.getName());
		this.setContent(selectedCategory);
	}

	private void setContent(Category category) {
		FragmentManager manager = this.getSupportFragmentManager();
		boolean isFirst = this.pinContent == null;

		FragmentTransaction transaction = manager.beginTransaction();

		PinListController controller = new PinListController(category, this);
		this.pinContent = new PinListFragment(controller);
		transaction.add(R.id.container, this.pinContent);
		transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);

		if (!isFirst) {
			transaction.addToBackStack(category.getName());
		}

		transaction.commit();
	}

	private void setTitle(String title) {
		if (this.firstTitle == null) {
			this.firstTitle = title;
		}
		this.getSupportActionBar().setTitle(title);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		this.menu = menu;
		this.getMenuInflater().inflate(R.menu.main, menu);
		MenuItem searchViewMenuItem = menu.findItem(R.id.action_search);
		this.searchView = (SearchView) searchViewMenuItem.getActionView();
		searchViewMenuItem.collapseActionView();
		SearchManager searchManager = (SearchManager) this
				.getSystemService(Context.SEARCH_SERVICE);
		this.searchView.setSearchableInfo(searchManager.getSearchableInfo(this
				.getComponentName()));
		this.searchView.setSubmitButtonEnabled(true);

		this.adjustOptionsMenu();
		return true;
	}

	private void adjustOptionsMenu() {
		if (this.menu == null) {
			return;
		}
		Log.i(this.getClass().getName(), "adjusting options menu");
		boolean loggedin = this.user.getIsLogedIn();
		this.menu.findItem(R.id.action_login).setVisible(!loggedin);
		this.menu.findItem(R.id.action_profile).setVisible(loggedin);
		this.menu.findItem(R.id.action_proximity).setVisible(loggedin);
		this.menu.findItem(R.id.action_logout).setVisible(loggedin);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (super.onOptionsItemSelected(item)) {
			return true;
		}

		Intent intent;

		switch (item.getItemId()) {
		case R.id.action_explore:
			intent = new Intent(this, BrowseBoardsActivity.class);
			this.startActivity(intent);
			return true;
		case R.id.action_settings:
			return true;
		case R.id.action_login:
			intent = new Intent(this, LoginActivity.class);
			this.startActivity(intent);
			return true;
		case R.id.action_search:
			intent = new Intent(this, SearchActivity.class);
			this.startActivity(intent);
			return true;
		case R.id.action_proximity:
			intent = new Intent(this, ProximityAlertActivity.class);
			this.startActivity(intent);
			return true;
		case R.id.action_profile:
			intent = new Intent(this, UserDetailsActivity.class);
			intent.putExtra(UserDetailsActivity.USER_ID_KEY, this.user.getId());
			this.startActivity(intent);
			return true;
		case R.id.action_logout:
			this.loginController.logout();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onSelected(Category selectedCategory) {
		this.setCategory(selectedCategory);
		if (this.drawerLayout != null) {
			this.drawerLayout.closeDrawers();
		}
	}
}
