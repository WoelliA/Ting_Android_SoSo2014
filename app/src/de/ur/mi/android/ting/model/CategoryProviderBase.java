package de.ur.mi.android.ting.model;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import javax.swing.event.ChangeListener;

import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public abstract class CategoryProviderBase implements IChangeListener<LoginResult>, ICategoryProvider {

	protected LocalUser user;
	protected List<Category> categories;
	
	private IChangeListener<List<Category>> categoryFavorityChangeListener;

	public CategoryProviderBase(LocalUser user) {
		this.user = user;
		this.user.addLoginChangeListener(this);
	}
	
	@Override
	public void getAllCategories(IDoneCallback<List<Category>> callback) {
		if(this.categories != null){
			callback.done(this.categories);
			callback = null;
		}
		this.categories = new ArrayList<Category>();
	}

	public String[] getCategoryNames(List<Category> categories) {
		String[] categorynames = new String[categories.size()];
		for (int i = 0; i < categories.size(); i++) {
			Category cate = categories.get(i);

			categorynames[i] = (cate.getName());
		}
		return categorynames;
	}

	@Override
	public void onChange(LoginResult result) {
		if(result.getIsRightLogin()){
			this.addFavoriteCategories(user, new SimpleDoneCallback<List<Category>>() {
				
				@Override
				public void done(List<Category> result) {
					notifyCategoriesChangeListener(result);
				}
			});			
		} else {
			List<Category> changed = new ArrayList<Category>();
			for (Category category : categories) {
				category.setIsFavorite(false);
				changed.add(category);
			}
			notifyCategoriesChangeListener(changed);
		}
	}
	
	protected void notifyCategoriesChangeListener(List<Category> categories) {
		if(categoryFavorityChangeListener != null)
			categoryFavorityChangeListener.onChange(categories);
	}

	@Override
	public void setCategoryFavoriteChangeListener(
			IChangeListener<List<Category>> listener) {
		this.categoryFavorityChangeListener = listener;
	}
}
