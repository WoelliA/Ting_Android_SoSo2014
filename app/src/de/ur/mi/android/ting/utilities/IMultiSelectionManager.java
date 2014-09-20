package de.ur.mi.android.ting.utilities;

import java.util.Collection;

public interface IMultiSelectionManager<T> extends ISelectionManager<T>{
	public Collection<T> getSelectedItems();
	public void setSelected(Collection<T> items, boolean selected);
}
