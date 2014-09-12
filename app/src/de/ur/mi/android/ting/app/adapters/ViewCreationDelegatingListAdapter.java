package de.ur.mi.android.ting.app.adapters;

import de.ur.mi.android.ting.utilities.view.ViewResolver;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


public class ViewCreationDelegatingListAdapter<T> extends ArrayAdapter<T> {

	
	private ViewResolver<T> viewResolver;

	public ViewCreationDelegatingListAdapter(Context context, ViewResolver<T> viewResolver) {
		super(context, 0);
		this.viewResolver = viewResolver;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return this.viewResolver.resolveView(this.getItem(position), convertView, parent);
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return this.viewResolver.resolveView(this.getItem(position), convertView, parent);
	}
}
