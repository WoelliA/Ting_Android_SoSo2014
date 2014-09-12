package de.ur.mi.android.ting.model.parse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import de.ur.mi.android.ting.model.CategoryProviderBase;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.ISpecialCategories;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.utilities.IDoneCallback;

@Singleton
public class ParseCategoryProvider extends CategoryProviderBase implements
		ICategoryProvider {

	public ParseCategoryProvider(LocalUser user) {
		super(user);
	}

	private List<Category> createCategories(List<ParseObject> arg) {
		ArrayList<Category> categories = new ArrayList<Category>();
		for (ParseObject parseObject : arg) {
			Category category = ParseHelper.createCategory(parseObject);
			categories.add(category);
		}
		return categories;
	}

	@Override
	public void getFavoriteCategories(LocalUser user,
			IDoneCallback<List<Category>> callback) {
		// TODO add favorite category for parse

	}

	@Override
	public void saveIsFavoriteCategory(Category category, boolean isChecked) {
		if (!this.user.getIsLogedIn()) {
			return;
		}

	}

	@Override
	protected void getAllCategoriesImpl(
			final IDoneCallback<List<Category>> callback) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("category");

		query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
		query.setMaxCacheAge(604800000);
		query.orderByAscending("category_name");
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> arg, ParseException ex) {
				if (ex == null) {
					ArrayList<Category> categories = new ArrayList<Category>();
					categories.addAll(ParseCategoryProvider.this
							.createCategories(arg));
					callback.done(categories);

				} else {
					Log.e("Parse Categories Exception", ex.getMessage());
				}
			}
		});
	}

}
