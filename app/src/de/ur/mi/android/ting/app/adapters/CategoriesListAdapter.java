package de.ur.mi.android.ting.app.adapters;

import java.util.HashMap;
import java.util.List;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.model.primitives.Category;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class CategoriesListAdapter extends ArrayAdapter<Category> implements
		IChangeListener<List<Category>> {

	private IChangeListener<Category> categoryFavorisedChangedListener;
	private List<Category> categories;
	private HashMap<Category, View> entries = new HashMap<Category, View>();

	public CategoriesListAdapter(Context context, List<Category> categories,
			IChangeListener<Category> categoryFavorisedChangedListener) {
		super(context, 0, categories);
		this.categories = categories;
		this.categoryFavorisedChangedListener = categoryFavorisedChangedListener;
	}

	@Override
	public View getView(int position, View itemView, ViewGroup parent) {

		itemView = View.inflate(this.getContext(), R.layout.category_layout,
				null);

		Category category = this.getItem(position);

		this.entries.put(category, itemView);

		ToggleButton favoriteToggleButton = (ToggleButton) itemView
				.findViewById(R.id.category_favorite_button);
		TextView categoryNameTextView = (TextView) itemView
				.findViewById(R.id.category_fragment_category_name);

		this.setIsFavoriteState(category, favoriteToggleButton);

		favoriteToggleButton
				.setOnCheckedChangeListener(new CategoryFavoriteChangeListener(
						category));

		categoryNameTextView.setText(category.getName());

		return itemView;
	}

	private void setIsFavoriteState(Category category,
			ToggleButton favoriteToggleButton) {
		if (favoriteToggleButton.isChecked() != category.getIsFavorite()) {
			favoriteToggleButton.setChecked(category.getIsFavorite());
		}
		if (category.getIsFavorite()) {
			this.adjustPosition(category, true);
		}
	}

	private void setIsFavoriteState(Category category) {
		View row = this.entries.get(category);
		if (row == null) {
			return;
		}

		ToggleButton button = (ToggleButton) row
				.findViewById(R.id.category_favorite_button);
		this.setIsFavoriteState(category, button);
	}

	private void adjustPosition(Category category, boolean isChecked) {
		this.remove(category);

		if (isChecked) {
			this.insert(category, 0);
		} else {

			for (int i = 0; i < this.categories.size(); i++) {
				Category c = this.categories.get(i);
				if (c.getIsFavorite()) {
					continue;
				}
				this.insert(category, i);
				break;
			}
		}
	}

	private class CategoryFavoriteChangeListener implements
			OnCheckedChangeListener {

		private Category category;

		public CategoryFavoriteChangeListener(Category category) {
			this.category = category;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			Log.i("category favorite", this.category.getName() + " "
					+ isChecked);
			this.category.setIsFavorite(isChecked);
			CategoriesListAdapter.this.adjustPosition(this.category, isChecked);
			CategoriesListAdapter.this.categoryFavorisedChangedListener.onChange(this.category);
		}

	}

	@Override
	public void onChange(List<Category> changed) {
		for (Category category : changed) {
			this.setIsFavoriteState(category);
		}
		this.notifyDataSetChanged();
	}
}
