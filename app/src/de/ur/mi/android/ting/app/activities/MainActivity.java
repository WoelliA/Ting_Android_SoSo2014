package de.ur.mi.android.ting.app.activities;

import javax.inject.Inject;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.ISelectedListener;
import de.ur.mi.android.ting.app.controllers.CategoriesController;
import de.ur.mi.android.ting.app.controllers.PinListController;
import de.ur.mi.android.ting.app.fragments.PinListFragment;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.Category;

public class MainActivity extends ActionBarActivityBase implements
		ISelectedListener<Category> {

	private DrawerLayout drawerLayout;
	private ListView categoryListView;
	private ActionBarDrawerToggle drawerListener;
	private Menu menu;

	PinListFragment pinContent;

	@Inject
	public LocalUser user;

	@Inject
	public CategoriesController categoryController;
	private SearchView searchView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		this.getSupportFragmentManager().addOnBackStackChangedListener(
				new OnBackStackChangedListener() {
					@Override
					public void onBackStackChanged() {
						FragmentManager manager = MainActivity.this
								.getSupportFragmentManager();
						int backstackCount = manager.getBackStackEntryCount();
						if (backstackCount > 0) {
							String name = manager.getBackStackEntryAt(
									backstackCount - 1).getName();
							MainActivity.this.setTitle(name);
						}
					}
				});
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
		if (getCurrentFocus() != null)
			getCurrentFocus().clearFocus();
		
		if (this.searchView != null) {
			this.searchView.setIconified(true);
		}
		
		this.categoryController.setSelectedCategoryChangeListener(this);
		this.categoryController.initCategories();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		this.initDrawer();
	}

	private void initDrawer() {
		this.drawerLayout = (DrawerLayout) this
				.findViewById(R.id.drawer_layout);
		this.drawerListener = new ActionBarDrawerToggle(this,
				this.drawerLayout, R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close);
		this.drawerLayout.setDrawerListener(this.drawerListener);

		this.getSupportActionBar().setHomeButtonEnabled(true);
		this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		this.drawerListener.syncState();
	}

	private void setSelectItem(int position) {
		this.categoryListView.setItemChecked(position, true);
	}

	private void setCategory(Category selectedCategory) {
		// setTitle(categoryName);
		this.setContent(selectedCategory);
	}

	private void setContent(Category category) {
		this.setTitle(category.getName());
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
		searchView.setSearchableInfo(searchManager.getSearchableInfo(this
				.getComponentName()));
		searchView.setSubmitButtonEnabled(true);

		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		Log.i("MainActivity", "onPrepareOptionsMenu");
		this.adjustOptionsMenu();
		return super.onPrepareOptionsMenu(menu);
	}

	private void adjustOptionsMenu() {
		if (this.menu == null) {
			return;
		}
		boolean visible = this.user.getIsLogedIn();
		this.menu.findItem(R.id.action_login).setVisible(!visible);
		this.menu.findItem(R.id.action_profile).setVisible(visible);
		this.menu.findItem(R.id.action_proximity).setVisible(visible);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (this.drawerListener.onOptionsItemSelected(item)) {
			return true;
		}

		Intent intent;

		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		case R.id.action_login:
			intent = new Intent(this, LoginActivity.class);
			this.startActivity(intent);
			return true;
		case R.id.action_search:
			intent = new Intent(this, SearchActivity.class);
			this.startActivity(intent);
		case R.id.action_proximity:
			intent = new Intent(this, ProximityAlertActivity.class);
			this.startActivity(intent);
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
