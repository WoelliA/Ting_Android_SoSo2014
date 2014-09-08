package de.ur.mi.android.ting.app.adapters;

import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.utilities.view.ViewResolver;
import android.content.Context;

public class CategoriesListAdapter extends
		ViewCreationDelegatingListAdapter<Category> {

	public CategoriesListAdapter(Context context, ViewResolver<Category> viewResolver) {
		super(context, viewResolver);
	}
}
