package de.ur.mi.android.ting.utilities.cache;

import java.lang.ref.Reference;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public abstract class MemoryCache<K, T> {

	/** Stores not strong references to objects */
	private final Map<K, Reference<T>> softMap = Collections
			.synchronizedMap(new HashMap<K, Reference<T>>());

	public MemoryCache() {
		Timer cleanupTimer = new Timer();
		long delay = this.getDelay();
		cleanupTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				for (K key : keys()) {
					try {

						if (get(key) == null)
							remove(key);
					} finally {
					}
				}

			}
		}, delay, delay);
	}

	private long getDelay() {
		return 50000;
	}

	public T get(K key) {
		T result = null;
		Reference<T> reference = softMap.get(key);
		if (reference != null) {
			result = reference.get();
		}
		return result;
	}

	public boolean has(K key) {
		if (softMap.containsKey(key)) {
			Reference<T> ref = softMap.get(key);
			if (ref != null) {
				if (ref.get() != null)
					return true;
			}
		}
		return false;
	}

	public boolean put(K key, T value) {
		softMap.put(key, createReference(value));
		return true;
	}

	public T remove(K key) {
		Reference<T> bmpRef = softMap.remove(key);
		return bmpRef == null ? null : bmpRef.get();
	}

	public Collection<K> keys() {
		synchronized (softMap) {
			return new HashSet<K>(softMap.keySet());
		}
	}

	public void clear() {
		softMap.clear();
	}

	/** Creates {@linkplain Reference not strong} reference of value */
	protected abstract Reference<T> createReference(T value);
}
