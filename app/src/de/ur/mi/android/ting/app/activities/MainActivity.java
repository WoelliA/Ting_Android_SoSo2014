package de.ur.mi.android.ting.app.activities;

import javax.inject.Inject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.fragments.PinListFragment;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.IStringArrayCallback;
import de.ur.mi.android.ting.model.IUser;
import de.ur.mi.android.ting.model.IUserService;

public class MainActivity extends ActionBarActivityBase implements
		OnItemClickListener {

	private DrawerLayout drawerLayout;
	private ListView categoryListView;
	private ActionBarDrawerToggle drawerListener;

	PinListFragment pinContent;

	@Inject
	public ICategoryProvider categoryProvider;
	@Inject
	public IUser user;
	private Menu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getActionBar().show();
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			this.categoryProvider
					.getAllCategoryNames(new IStringArrayCallback() {
						@Override
						public void onStringArrayReceived(String[] strings) {
							initDrawer(strings);
							setCategory(strings[0]);
						}
					});
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		this.adjustOptionsMenu();
	}

	private void initDrawer(String[] categories) {

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerListener = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close);
		drawerLayout.setDrawerListener(drawerListener);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		categoryListView = (ListView) findViewById(R.id.drawer_list);

		categoryListView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, categories));
		categoryListView.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String categoryName = (String) ((TextView) view).getText();

		setSelectItem(position);
		setCategory(categoryName);
		drawerLayout.closeDrawers();
	}

	private void setSelectItem(int position) {
		categoryListView.setItemChecked(position, true);
	}

	private void setCategory(String categoryName) {
		// setTitle(categoryName);
		setContent(categoryName);
	}

	private void setContent(String categoryName) {

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		if (this.pinContent != null)
			transaction.detach(this.pinContent);
		this.pinContent = new PinListFragment(categoryName);
		transaction.add(R.id.container, this.pinContent);
		transaction.addToBackStack(categoryName);
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
		return true;
	}

	private void adjustOptionsMenu() {
		if(menu == null)
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
<<<<<<< HEAD
		case R.id.action_settings:
			return true;
		case R.id.action_login:
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
=======
		case R.id.action_search:
			intent = new Intent (this, SearchActivity.class);
			startActivity(intent);
	    case R.id.action_settings:
	        return true;
	    case R.id.action_login:
	    	intent = new Intent(this, LoginActivity.class);
	    	startActivity(intent);
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
>>>>>>> origin/master
		}
	}

}
