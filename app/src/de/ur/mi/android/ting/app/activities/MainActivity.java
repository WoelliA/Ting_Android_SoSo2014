package de.ur.mi.android.ting.app.activities;

import javax.inject.Inject;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.ISelectedListener;
import de.ur.mi.android.ting.app.controllers.CategoriesController;
import de.ur.mi.android.ting.app.controllers.PinListController;
import de.ur.mi.android.ting.app.fragments.CategoriesFragment;
import de.ur.mi.android.ting.app.fragments.PinListFragment;
import de.ur.mi.android.ting.model.IPinProvider;
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
	public IPinProvider pinProvider;

	@Inject
	public LocalUser user;
	
	@Inject 
	public CategoriesController categoryChangedEmitter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		categoryChangedEmitter.setSelectedCategoryChangeListener(this);

		if (this.checkInternetConnection() == false) {
			this.showAlertNoInternetConnection();
		}

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

	private void showAlertNoInternetConnection() {
		AlertDialog.Builder connectionDialog = new AlertDialog.Builder(this);
		connectionDialog.setTitle(this
				.getString(R.string.connection_error_title));
		connectionDialog.setMessage(this
				.getString(R.string.connection_error_content));
		connectionDialog.setNeutralButton(R.string.button_dismiss,
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

					}
				});
		connectionDialog.setCancelable(true);
		connectionDialog.setIcon(android.R.drawable.ic_dialog_alert);
		connectionDialog.show();
	}

	private boolean checkInternetConnection() {

		ConnectivityManager cm = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.adjustOptionsMenu();
		if (this.checkInternetConnection() == false) {
			this.showAlertNoInternetConnection();
		}
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

		PinListController controller = new PinListController(category,
				this.pinProvider);
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
		this.adjustOptionsMenu();

		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();
		SearchManager searchManager = (SearchManager) this
				.getSystemService(Context.SEARCH_SERVICE);
		searchView.setSearchableInfo(searchManager.getSearchableInfo(this
				.getComponentName()));
		searchView.setSubmitButtonEnabled(true);

		return true;
	}

	private void adjustOptionsMenu() {
		if (this.menu == null) {
			return;
		}
		boolean visible = this.user.getIsLogedIn();
		this.menu.findItem(R.id.action_login).setVisible(!visible);
		this.menu.findItem(R.id.action_profile).setVisible(visible);
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
