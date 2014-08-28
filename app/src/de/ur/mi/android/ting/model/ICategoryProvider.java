package de.ur.mi.android.ting.model;

import de.ur.mi.android.ting.model.Primitives.Category;

public interface ICategoryProvider {
	public void getAllCategories(ICategoryReceivedCallback callback);
	public void getAllCategoryNames(IStringArrayCallback callback);
	public Category resolveCategoryByName(String name);
}
