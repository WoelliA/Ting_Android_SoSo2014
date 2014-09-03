package de.ur.mi.android.ting.utilities.view;

import android.util.SparseArray;
import android.view.View;

public class ViewHolder {

	private SparseArray<View> resolvedViews;
	private View view;

	public ViewHolder(View view) {
		this.view = view;
		this.resolvedViews = new SparseArray<View>();
	}

	public View getView(int id) {
		View view = this.resolvedViews.get(id, null);
		if (view != null) {
			return view;
		}
		view = this.view.findViewById(id);
		this.resolvedViews.put(id, view);
		return view;
	}

}