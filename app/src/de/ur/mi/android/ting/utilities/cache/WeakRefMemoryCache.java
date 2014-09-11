package de.ur.mi.android.ting.utilities.cache;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public class WeakRefMemoryCache<K,T> extends MemoryCache<K,T> {

	@Override
	protected Reference<T> createReference(T value) {
		return new WeakReference<T>(value);
	}

}
