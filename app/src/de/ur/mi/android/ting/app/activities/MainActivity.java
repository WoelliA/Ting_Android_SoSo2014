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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.ISelectedListener;
import de.ur.mi.android.ting.app.fragments.CategoriesFragment;
import de.ur.mi.android.ting.app.fragments.PinListFragment;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.IStringArrayCallback;
import de.ur.mi.android.ting.model.IUserService;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		CategoriesFragment f = (CategoriesFragment) getSupportFragmentManager()
				.findFragmentById(R.id.categories_fragment);
		f.setCategorySelectedListener(this);
		this.getSupportFragmentManager().addOnBackStackChangedListener(
				new OnBackStackChangedListener() {
					@Override
					public void onBackStackChanged() {
						if (getSupportFragmentManager()
								.getBackStackEntryCount() == 0) {
							return;
						}

						FragmentManager manager = getSupportFragmentManager();
						String name = manager.getBackStackEntryAt(
								getSupportFragmentManager()
										.getBackStackEntryCount() - 1)
								.getName();
						setTitle(name);
					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.adjustOptionsMenu();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		initDrawer();
	}

	private void initDrawer() {
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerListener = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close);
		drawerLayout.setDrawerListener(drawerListener);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		drawerListener.syncState();
	}

	private void setSelectItem(int position) {
		categoryListView.setItemChecked(position, true);
	}

	private void setCategory(Category selectedCategory) {
		// setTitle(categoryName);
		setContent(selectedCategory);
	}

	private void setContent(Category category) {
		this.setTitle(category.getName());
		FragmentManager manager = getSupportFragmentManager();
		boolean isFirst = this.pinContent == null;

		FragmentTransaction transaction = manager.beginTransaction();

		this.pinContent = new PinListFragment(category);
		transaction.add(R.id.container, this.pinContent);
		transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
		
		if(!isFirst)
			transaction.addToBackStack(category.getName());
		
		transaction.commit();
	}

	private void setTitle(String title) {
		getSupportActionBar().setTitle(title);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		this.menu = menu;
		getMenuInflater().inflate(R.menu.main, menu);
		this.adjustOptionsMenu();

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		searchView.setSubmitButtonEnabled(true);

		return true;
	}

	private void adjustOptionsMenu() {
		if (menu == null)
			return;
		boolean visible = this.user.getIsLogedIn();
		menu.findItem(R.id.action_login).setVisible(!visible);
		menu.findItem(R.id.action_profile).setVisible(visible);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerListener.onOptionsItemSelected(item)) {
			return true;
		}

		Intent intent;

		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		case R.id.action_login:
			intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			return true;
		case R.id.action_search:
			intent = new Intent(this, SearchActivity.class);
			startActivity(intent);
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onSelected(Category selectedCategory) {
		setCategory(selectedCategory);
		if (drawerLayout != null) {
			drawerLayout.closeDrawers();

		}
	}

}
