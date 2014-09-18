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
				for (K key : MemoryCache.this.keys()) {
					try {

						if (MemoryCache.this.get(key) == null) {
							MemoryCache.this.remove(key);
						}
					} finally {
					}
				}

			}
		}, delay, delay);
	}

	protected long getDelay() {
		return 60000;
	}

	public T get(K key) {
		T result = null;
		Reference<T> reference = this.softMap.get(key);
		if (reference != null) {
			result = reference.get();
		}
		return result;
	}

	public boolean has(K key) {
		if (this.softMap.containsKey(key)) {
			Reference<T> ref = this.softMap.get(key);
			if (ref != null) {
				if (ref.get() != null) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean put(K key, T value) {
		this.softMap.put(key, this.createReference(value));
		return true;
	}

	public T remove(K key) {
		Reference<T> bmpRef = this.softMap.remove(key);
		return bmpRef == null ? null : bmpRef.get();
	}

	public Collection<K> keys() {
		synchronized (this.softMap) {
			return new HashSet<K>(this.softMap.keySet());
		}
	}

	public void clear() {
		this.softMap.clear();
	}

	/** Creates {@linkplain Reference not strong} reference of value */
	protected abstract Reference<T> createReference(T value);
}
