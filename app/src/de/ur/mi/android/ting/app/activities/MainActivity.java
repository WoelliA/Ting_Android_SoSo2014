package de.ur.mi.android.ting.app.activities;

import java.util.ArrayList;
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
import de.ur.mi.android.ting.app.adapters.PinListAdapter;
import de.ur.mi.android.ting.model.IArticleProvider;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.ICategoryReceivedCallback;
import de.ur.mi.android.ting.model.Primitives.Category;
import de.ur.mi.android.ting.model.Primitives.Pin;

public class MainActivity extends ActionBarActivity implements
		OnItemClickListener {

	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private String[] drawerItems;
	private ActionBarDrawerToggle drawerListener;
	
	private ListView pinList;
	private ArrayList<Pin> pinItems;
	private PinListAdapter pinListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {

			
			initPinList();
			initDrawer();
		}
	}

	private void initPinList() {
		pinList = (ListView)findViewById(R.id.pin_list);
		pinItems = new ArrayList<Pin>();
		pinListAdapter = new PinListAdapter(this, pinItems);
		pinList.setAdapter(pinListAdapter);
	}

	private void initDrawer() {
		
		// TODO: use drawerFragment to fill list & set Adapter etc.
		// using the categoryProvider there.
		
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerListener = new ActionBarDrawerToggle(this, drawerLayout,R.drawable.ic_drawer, R.string.drawer_open,R.string.drawer_close);
		drawerLayout.setDrawerListener(drawerListener);
		drawerItems = getResources().getStringArray(R.array.drawer_categories); // Zu
																				// ersetzen
																				// mit
																				// Strings
																				// der
																				// Categories
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		drawerList = (ListView) findViewById(R.id.drawer_list);
		drawerList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, drawerItems));
		drawerList.setOnItemClickListener(this);
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
		drawerList.setItemChecked(position, true);
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

}
