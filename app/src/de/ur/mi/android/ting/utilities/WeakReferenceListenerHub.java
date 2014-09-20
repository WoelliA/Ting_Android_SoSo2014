package de.ur.mi.android.ting.utilities;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import de.ur.mi.android.ting.model.primitives.Category;

public class WeakReferenceListenerHub<T> implements Iterable<T> {

	private List<WeakReference<T>> listeners = new ArrayList<WeakReference<T>>();

	public void addListener(T listener) {
		listeners.add(new WeakReference<T>(listener));
	}

	@Override
	public Iterator<T> iterator() {
		List<T> objects = new ArrayList<T>();
		for (WeakReference<T> weakReference : listeners) {
			T t = weakReference.get();
			if (t != null) {
				objects.add(t);
			}
		}
		return objects.iterator();
	}

}
