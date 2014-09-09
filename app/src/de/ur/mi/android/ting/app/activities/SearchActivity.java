package de.ur.mi.android.ting.app.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.ISelectedListener;
import de.ur.mi.android.ting.app.controllers.SearchController;
import de.ur.mi.android.ting.app.fragments.SearchResultFragment;
import de.ur.mi.android.ting.app.viewResolvers.SearchResultResolvers;
import de.ur.mi.android.ting.model.IPaging;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.model.primitives.SearchType;
import de.ur.mi.android.ting.model.primitives.User;
import de.ur.mi.android.ting.utilities.view.ViewResolver;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

public class SearchActivity extends ActionBarActivityBase implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If thiss
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;

	@Inject
	public SearchController searchController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_search);
		this.initUi();
		this.initData(this.getIntent());
	}

	private void initUi() {
		// Set up the action bar.
		final ActionBar actionBar = this.getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		List<SearchResultFragment<?>> fragments = this.initFragments();
		this.mSectionsPagerAdapter = new SectionsPagerAdapter(
				this.getSupportFragmentManager(), fragments);

		// Set up the ViewPager with the sections adapter.
		this.mViewPager = (ViewPager) this.findViewById(R.id.pager);
		this.mViewPager.setAdapter(this.mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		this.mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
						SearchActivity.this.mViewPager.setCurrentItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < this.mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(this.mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.search, menu);
		MenuItem searchItem = menu.findItem(R.id.search_action_search);
		SearchView searchView = (SearchView) MenuItemCompat
				.getActionView(searchItem);
		searchView.setIconifiedByDefault(false);
		searchView.setSubmitButtonEnabled(true);
		searchView.setQuery(this.searchController.getQuery(), false);
		SearchManager searchManager = (SearchManager) this
				.getSystemService(Context.SEARCH_SERVICE);
		searchView.setSearchableInfo(searchManager.getSearchableInfo(this
				.getComponentName()));
		return super.onCreateOptionsMenu(menu);
	}

	private List<SearchResultFragment<?>> initFragments() {
		ArrayList<SearchResultFragment<?>> fragments = new ArrayList<SearchResultFragment<?>>();

		fragments.add(this.createFragment(R.string.search_pins_header,
				SearchType.PIN,
				new SearchResultResolvers.PinSearchResultViewResolver(this),
				new ISelectedListener<Pin>() {

					@Override
					public void onSelected(Pin selectedItem) {
						// TODO show pin details

					}
				}));
		fragments.add(this.createFragment(R.string.search_user_header,
				SearchType.USER, new SearchResultResolvers.UserResolver(this),
				new ISelectedListener<User>() {

					@Override
					public void onSelected(User selectedItem) {
						// TODO show user details
					}

				}));

		fragments.add(this.createFragment(R.string.search_boards_header,
				SearchType.BOARD,
				new SearchResultResolvers.BoardResolver(this),
				new ISelectedListener<Board>() {

					@Override
					public void onSelected(Board selectedItem) {
						Intent intent = new Intent(SearchActivity.this,
								BoardDetailsActivity.class);
						intent.putExtra(BoardDetailsActivity.BOARD_ID_KEY,
								selectedItem.getId());
						startActivity(intent);
					}
				}));

		return fragments;
	}

	private <T> SearchResultFragment<T> createFragment(int titleStringId,
			SearchType type, ViewResolver<T> viewResolver,
			ISelectedListener<T> listener) {
		IPaging<T> pagingController = this.searchController
				.getPagingController(type);
		return new SearchResultFragment<T>(this.getString(titleStringId),
				viewResolver, pagingController, listener);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		this.setIntent(intent);
		this.initData(intent);
	}

	private void initData(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			this.searchController.onNewQuery(query);
		}
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		this.mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

		private List<SearchResultFragment<?>> fragments;

		public SectionsPagerAdapter(FragmentManager fm,
				List<SearchResultFragment<?>> fragments) {
			super(fm);
			this.fragments = fragments;
		}

		@Override
		public Fragment getItem(int position) {
			return this.fragments.get(position);
		}

		@Override
		public int getCount() {
			if (this.fragments == null) {
				return -1;
			}
			return this.fragments.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			String title = ((SearchResultFragment<?>) this.getItem(position))
					.getTitle();
			return title.toUpperCase(l);
		}
	}
}
