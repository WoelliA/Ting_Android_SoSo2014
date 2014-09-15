package de.ur.mi.android.ting.model.parse;

import java.util.List;

import javax.inject.Singleton;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import de.ur.mi.android.ting.model.CategoryProviderBase;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.utilities.IDoneCallback;

@Singleton
public class ParseCategoryProvider extends CategoryProviderBase implements
		ICategoryProvider {

	public ParseCategoryProvider(LocalUser user, IUserService userService) {
		super(user, userService);
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
					callback.done(ParseHelper.createCategories(arg));

				} else {
					Log.e("Parse Categories Exception", ex.getMessage());
				}
			}
		});
	}

}
