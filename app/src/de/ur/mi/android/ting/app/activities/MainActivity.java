package de.ur.mi.android.ting.app.activities;

import java.util.List;

import javax.inject.Inject;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.IInjector;
import de.ur.mi.android.ting.model.IArticleProvider;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.ICategoryReceivedCallback;
import de.ur.mi.android.ting.model.Primitives.Category;

public class MainActivity extends ActionBarActivity implements
		OnItemClickListener {

	private DrawerLayout drawerLayout;
	private ListView listView;
	private String[] drawerItems;
	private ActionBarDrawerToggle drawerListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();

			initDrawer();
		}
	}

	private void initDrawer() {
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerListener = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close);
		drawerLayout.setDrawerListener(drawerListener);
		drawerItems = getResources().getStringArray(R.array.drawer_categories); // Zu
																				// ersetzen
																				// mit
																				// Strings
																				// der
																				// Categories
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		listView = (ListView) findViewById(R.id.drawer_list);
		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, drawerItems));
		listView.setOnItemClickListener(this);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerListener.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerListener.syncState();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		selectItem(position);
		drawerLayout.closeDrawers();
	}

	private void selectItem(int position) {
		listView.setItemChecked(position, true);
		replaceFragment(position);
	}

	private void replaceFragment(int position) {
		setTitle(drawerItems[position]);
	}

	private void setTitle(String title) {
		getSupportActionBar().setTitle(title);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerListener.onOptionsItemSelected(item)) {
			return true;
		}

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_pinlist,
					container, false);
			return rootView;
		}
	}

}
