package de.ur.mi.android.ting.model.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Singleton;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import de.ur.mi.android.ting.model.CategoryProviderBase;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.ICategoryReceivedCallback;
import de.ur.mi.android.ting.model.IStringArrayCallback;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.utilities.IDoneCallback;

@Singleton
public class ParseCategoryProvider extends CategoryProviderBase implements
		ICategoryProvider {

	public ParseCategoryProvider(LocalUser user) {
		super(user);
	}


	@Override
	public void getAllCategories(final IDoneCallback<List<Category>> callback) {
		super.getAllCategories(callback);
		if(callback == null || callback.getIsDone())
			return;
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("category");
		
		query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
		query.setMaxCacheAge(604800000);


		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> arg, ParseException ex) {
				if (ex == null) {
					categories = createCategories(arg);
					callback.done(categories);					

				} else {
					Log.e("Parse Categories Exception", ex.getMessage());
				}
			}
		});
	}


	private ArrayList<Category> createCategories(List<ParseObject> arg) {
		ArrayList<Category> categories = new ArrayList<Category>();
		for (ParseObject parseObject : arg) {
			Category category = ParseHelper.createCategory(parseObject);
			categories.add(category);
		}
		return categories;
	}

	@Override
	public void addFavoriteCategories(LocalUser user,
			IDoneCallback<List<Category>> callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveIsFavoriteCategory(Category category, boolean isChecked) {
		if(!this.user.getIsLogedIn())
			return;
		
	}

}
