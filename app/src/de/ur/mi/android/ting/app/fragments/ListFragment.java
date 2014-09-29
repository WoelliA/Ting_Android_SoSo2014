package de.ur.mi.android.ting.app.fragments;

import java.util.ArrayList;
import java.util.List;

import de.ur.mi.android.ting.app.ISelectedListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public abstract class ListFragment<T> extends FragmentBase implements
		ICollectionView {

	private List<T> items;
	private ArrayAdapter<T> adapter;
	protected ISelectedListener<T> selectedListener;

	public ListFragment() {
		this.items = new ArrayList<T>();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ListView listView = this.getListView(this.getView());
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (ListFragment.this.selectedListener != null) {
					ListFragment.this.selectedListener
							.onSelected(ListFragment.this.adapter
									.getItem(position));
				}
			}
		});
		this.adapter = this.getListAdapter();
		this.adapter.addAll(this.items);
		this.items.clear();
		listView.setAdapter(this.adapter);
	}

	@Override
	public void add(Object item) {
		T obj = (T) item;
		if (this.adapter == null) {
			this.items.add(obj);
		} else {
			this.adapter.add(obj);
		}
	}

	@Override
	public void addAll(Object[] items) {
		for (Object object : items) {
			this.add(object);
		}
	}

	@Override
	public void set(Object[] items) {
		this.adapter.clear();
		this.addAll(items);
	}

	protected abstract ListView getListView(View view);

	protected abstract ArrayAdapter<T> getListAdapter();

	public void setSelectListener(ISelectedListener<T> selectedListener) {
		this.selectedListener = selectedListener;

	}
}
