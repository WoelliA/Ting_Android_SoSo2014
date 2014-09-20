package de.ur.mi.android.ting.utilities;

import de.ur.mi.android.ting.app.ISelectedListener;
import de.ur.mi.android.ting.model.primitives.Category;

public interface IMultiSelectionListener<T> extends ISelectedListener<T>{
	public void onUnSelect(T data);

	void setManager(IMultiSelectionManager<T> manager);
}
