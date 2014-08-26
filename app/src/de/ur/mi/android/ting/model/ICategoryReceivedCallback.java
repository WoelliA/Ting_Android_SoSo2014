package de.ur.mi.android.ting.model;

import java.util.List;

import de.ur.mi.android.ting.model.Primitives.Category;

public interface ICategoryReceivedCallback {
	public void onCategoriesReceived(List<Category> categories);
}
