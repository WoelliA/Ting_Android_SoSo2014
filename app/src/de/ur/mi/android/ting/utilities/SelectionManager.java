package de.ur.mi.android.ting.utilities;

import java.util.HashSet;

import android.view.View;
import de.ur.mi.android.ting.app.ISelectedListener;
import de.ur.mi.android.ting.model.primitives.Category;

public class SelectionManager<T> implements ISelectionManager<T> {

	protected ISelectedListener<T> selectionListener;
	protected HashSet<T> selectedItems;

	public SelectionManager(ISelectedListener<T> selectionListener) {
		this.selectionListener = selectionListener;
		selectedItems = new HashSet<T>();
	}

	public void setSelectedState(View selectable, T data) {
		selectable.setSelected(false);
		if (isSelected(data)) {
			selectable.setSelected(true);
		}
	}

	private boolean isSelected(T data) {
		return selectedItems.contains(data);
	}

	public void toggleSelected(View selectable, T data) {
		boolean newState = !isSelected(data);
		addOrRemove(newState, data);
		notifyListener(newState, data);
		selectable.setSelected(newState);
	}

	protected void notifyListener(boolean newState, T data) {
		if (this.selectionListener == null) {
			return;
		}
		if (newState) {
			this.selectionListener.onSelected(data);
		} else {
			// this.selectionListener.onUnSelect(data);
		}
	}

	protected void addOrRemove(boolean newSelectedState, T data) {
		if (newSelectedState) {
			this.selectedItems.add(data);
		} else {
			this.selectedItems.remove(data);
		}
	}

	@Override
	public void setSelected(T item, boolean selected) {
		addOrRemove(selected, item);	
		notifyListener(selected, item);
	}
}
