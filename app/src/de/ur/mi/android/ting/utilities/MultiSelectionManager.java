package de.ur.mi.android.ting.utilities;

import java.util.Collection;

import de.ur.mi.android.ting.app.ISelectedListener;

public class MultiSelectionManager<T> extends SelectionManager<T> implements
		IMultiSelectionManager<T> {

	public MultiSelectionManager(IMultiSelectionListener<T> selectionListener) {
		super(selectionListener);
		selectionListener.setManager(this);
	}

	@Override
	public Collection<T> getSelectedItems() {
		return this.selectedItems;
	}
	
	@Override
	protected void notifyListener(boolean newState, T data) {
		super.notifyListener(newState, data);
		if(selectionListener != null && !newState){
			((IMultiSelectionListener<T>) selectionListener).onUnSelect(data);
		}
	}

	@Override
	public void setSelected(Collection<T> items, boolean selected) {
		for (T t : items) {
			this.setSelected(t, selected);
		}		
	}
	

}
