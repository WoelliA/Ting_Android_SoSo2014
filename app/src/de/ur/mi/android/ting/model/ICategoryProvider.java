package de.ur.mi.android.ting.model;

import java.util.List;

import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public interface ICategoryProvider {
	public void getAllCategories(IDoneCallback<List<Category>> callback);
	public void addFavoriteCategories(LocalUser user, IDoneCallback<List<Category>> callback);
	public void saveIsFavoriteCategory(Category category, boolean isChecked);
	
	public void setCategoryFavoriteChangeListener(IChangeListener<List<Category>> listener);
}
