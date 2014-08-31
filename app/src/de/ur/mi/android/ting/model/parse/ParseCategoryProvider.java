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
import de.ur.mi.android.ting.model.primitives.Category;

@Singleton
public class ParseCategoryProvider extends CategoryProviderBase implements
		ICategoryProvider {

	protected ArrayList<Category> categories;
	protected HashMap<String, Category> categoriesMap = new HashMap<String, Category>();

	@Override
	public void getAllCategories(final ICategoryReceivedCallback callback) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("category");
		
		query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
		query.setMaxCacheAge(604800000);
		
		if (categories != null)
			callback.onCategoriesReceived(categories);

		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> arg, ParseException ex) {
				if (ex == null) {
					categories = createCategories(arg);
					callback.onCategoriesReceived(categories);					

				} else {
					Log.e("Parse Categories Exception", ex.getMessage());
				}
			}
		});
	}

	@Override
	public Category resolveCategoryByName(String name) {
		if (this.categoriesMap != null && this.categoriesMap.containsKey(name)) {
			return this.categoriesMap.get(name);
		}
		return null;
	}

	protected Category createCategory(ParseObject parseObject) {
		Category category = new Category(parseObject.getObjectId(),
				parseObject.getString("category_name"),
				parseObject.getString("short"));
		return category;
	}

	private ArrayList<Category> createCategories(List<ParseObject> arg) {
		ArrayList<Category> categories = new ArrayList<Category>();
		for (ParseObject parseObject : arg) {
			Category category = createCategory(parseObject);
			categoriesMap.put(category.getName(), category);
			categories.add(category);
		}
		return categories;
	}

	@Override
	public void getAllCategoryNames(final IStringArrayCallback callback) {
		this.getAllCategories(new ICategoryReceivedCallback() {

			@Override
			public void onCategoriesReceived(List<Category> categories) {
				callback.onStringArrayReceived(getCategoryNames(categories));
			}
		});

	}

}
