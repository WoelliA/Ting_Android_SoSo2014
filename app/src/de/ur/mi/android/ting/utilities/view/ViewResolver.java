package de.ur.mi.android.ting.utilities.view;

import de.ur.mi.android.ting.app.IInjectable;
import de.ur.mi.android.ting.app.IInjector;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public abstract class ViewResolver<T> implements IInjectable {
	private int resourceId;
	protected Context context;

	public ViewResolver(int resourceId, Context context) {
		this.resourceId = resourceId;
		this.context = context;
		((IInjector) context).inject(this);
	}

	protected Context getContext() {
		return this.context;
	}

	public View resolveView(T dataItem, View view, ViewGroup parent) {
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(this.resourceId, parent, false);
		}
		LayoutParams lp = view.getLayoutParams();
		lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		view.setLayoutParams(lp);
		this.decorateView(view, dataItem, parent);
		return view;
	}

	protected View findViewById(View view, int id) {
		ViewHolder holder = this.getViewHolder(view);
		return holder.getView(id);
	}

	private ViewHolder getViewHolder(View view) {
		int holderId = "holder".hashCode();
		Object tag = view.getTag(holderId);
		if (tag == null) {
			ViewHolder holder = new ViewHolder(view);
			view.setTag(holderId, holder);
			return holder;
		}
		return (ViewHolder) tag;
	}

	protected abstract void decorateView(View view, T dataItem, ViewGroup parent);

	@Override
	public boolean skipInject() {
		return false;
	}
}
