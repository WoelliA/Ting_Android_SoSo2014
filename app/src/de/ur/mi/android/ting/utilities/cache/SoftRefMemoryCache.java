package de.ur.mi.android.ting.utilities.cache;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

public class SoftRefMemoryCache<K,T> extends MemoryCache<K, T>{

	@Override
	protected Reference<T> createReference(T value) {
		return new SoftReference<T>(value);
	}
	
}
