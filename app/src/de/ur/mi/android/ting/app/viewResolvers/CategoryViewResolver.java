package de.ur.mi.android.ting.app.viewResolvers;

import javax.inject.Inject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.activities.LoginActivity;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.SpecialCategories.SpecialCategory;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.utilities.IBiChangeListener;
import de.ur.mi.android.ting.utilities.view.Notify;
import de.ur.mi.android.ting.utilities.view.SimpleYesNoCallback;
import de.ur.mi.android.ting.utilities.view.ViewResolver;

public class CategoryViewResolver extends ViewResolver<Category> {

	@Inject
	public LocalUser user;

	private IBiChangeListener<Category, Boolean> favoriteChangeCallback;

	public CategoryViewResolver(int resourceId, Context context,
			IBiChangeListener<Category, Boolean> favoriteChangeCallback) {
		super(resourceId, context);
		this.favoriteChangeCallback = favoriteChangeCallback;
	}

	@Override
	protected void decorateView(View view, Category category, ViewGroup parent) {

		ToggleButton favoriteToggleButton = (ToggleButton) this.findViewById(
				view, R.id.category_favorite_button);
		TextView categoryNameTextView = (TextView) this.findViewById(view,
				R.id.category_fragment_category_name);

		categoryNameTextView.setText(category.getName());

		favoriteToggleButton.setOnCheckedChangeListener(null);

		this.setIsFavoriteState(category, favoriteToggleButton);

		favoriteToggleButton
				.setOnCheckedChangeListener(new CategoryFavoriteChangeListener(
						category, this.favoriteChangeCallback));

		if (category instanceof SpecialCategory) {
			favoriteToggleButton.setVisibility(View.INVISIBLE);
		} else {
			favoriteToggleButton.setVisibility(View.VISIBLE);
		}

	}

	private void setIsFavoriteState(Category category,
			ToggleButton favoriteToggleButton) {
		favoriteToggleButton.setChecked(category.getIsFavorite());
	}

	private class CategoryFavoriteChangeListener implements
			OnCheckedChangeListener {

		private Category category;
		private IBiChangeListener<Category, Boolean> callback;

		public CategoryFavoriteChangeListener(Category category,
				IBiChangeListener<Category, Boolean> favoriteChangeCallback) {
			this.category = category;
			this.callback = favoriteChangeCallback;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (!CategoryViewResolver.this.user.getIsLogedIn()) {
				if (isChecked) {
					Notify.current().showYesNoDialog(R.string.action_login,

					R.string.favorite_category_needs_login,
							R.string.action_login, new SimpleYesNoCallback() {

								@Override
								public void onYes() {
									Intent intent = new Intent(CategoryViewResolver.this.context,
											LoginActivity.class);
									CategoryViewResolver.this.context.startActivity(intent);
								}
							});
					buttonView.setChecked(false);
				}

				return;
			}
			Log.i("category favorite", this.category.getName() + " "
					+ isChecked);
			this.category.setIsFavorite(isChecked);
			this.callback.onChange(this.category, isChecked);
		}

	}

}
