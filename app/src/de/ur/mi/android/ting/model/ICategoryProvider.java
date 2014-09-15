package de.ur.mi.android.ting.model;

import java.util.Collection;
import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public interface ICategoryProvider {
	public void getAllCategories(IDoneCallback<Collection<Category>> callback);

	public void setCategoriesChangedListener(
			IChangeListener<Collection<Category>> listener);
}
