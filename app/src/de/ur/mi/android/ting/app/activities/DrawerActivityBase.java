package de.ur.mi.android.ting.app.activities;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.ISelectedListener;
import de.ur.mi.android.ting.model.primitives.Category;

public class DrawerActivityBase extends BaseActivity {

	protected DrawerLayout drawerLayout;
	protected ActionBarDrawerToggle drawerListener;

	private int drawerLayoutId = R.id.drawer_layout;

	public DrawerActivityBase() {
		super();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		this.initDrawer();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (this.drawerListener.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void setDrawerLayoutId(int drawerLayoutId) {
		this.drawerLayoutId = drawerLayoutId;
	}

	protected void initDrawer() {
		this.drawerLayout = (DrawerLayout) this.findViewById(drawerLayoutId);
		this.drawerListener = new ActionBarDrawerToggle(this,
				this.drawerLayout, R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close);
		this.drawerLayout.setDrawerListener(this.drawerListener);

		this.getSupportActionBar().setHomeButtonEnabled(true);
		this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		this.drawerListener.syncState();
	}

}