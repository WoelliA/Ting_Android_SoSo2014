package de.ur.mi.android.ting.app.fragments;

import java.util.List;

import javax.inject.Inject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.app.ISelectedListener;
import de.ur.mi.android.ting.app.adapters.CategoriesListAdapter;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public class CategoriesFragment extends BaseFragment implements
IChangeListener<Category> {

	@Inject
	public ICategoryProvider categoryProvider;

	private ListView categoriesListView;
	private CategoriesListAdapter categoriesAdapter;

	private ISelectedListener<Category> selectedListener;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.initUi(this.getActivity());
		this.initData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_categories, container, false);
	}

	private void initData() {
		this.categoryProvider
				.getAllCategories(new SimpleDoneCallback<List<Category>>() {
					@Override
					public void done(List<Category> result) {
						selectedListener.onSelected(result.get(0));
						fillList(result);
						categoriesListView.setSelection(0);
					}
				});
	}

	protected void fillList(List<Category> categories) {
		this.categoriesAdapter = new CategoriesListAdapter(getActivity(),
				categories, this);
		this.categoriesListView.setAdapter(categoriesAdapter);
		this.categoryProvider
				.setCategoryFavoriteChangeListener(this.categoriesAdapter);
	}

	private void initUi(Activity parent) {
		this.categoriesListView = (ListView) parent
				.findViewById(R.id.categories_list);
		categoriesListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (selectedListener != null) {
					Category selectedCategory = categoriesAdapter
							.getItem(position);
					selectedListener.onSelected(selectedCategory);
				}
			}
		});
	}

	public void setCategorySelectedListener(
			ISelectedListener<Category> selectedListener) {
		this.selectedListener = selectedListener;
	}


	@Override
	public void onChange(Category category) {
		this.categoryProvider.saveIsFavoriteCategory(category, category.getIsFavorite());		
	}
}