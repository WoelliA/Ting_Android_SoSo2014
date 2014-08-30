package de.ur.mi.android.ting.model;

import java.util.List;

import de.ur.mi.android.ting.model.primitives.Category;

public class CategoryProviderBase {
	public String[] getCategoryNames(List<Category> categories) {
		String[] categorynames = new String[categories.size()];
		for (int i = 0; i < categories.size(); i++) {
			Category cate = categories.get(i);

			categorynames[i]=(cate.getName());
		}
		return categorynames;
	}
}
