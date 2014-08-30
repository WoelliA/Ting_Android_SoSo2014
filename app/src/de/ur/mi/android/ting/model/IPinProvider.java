package de.ur.mi.android.ting.model;

import de.ur.mi.android.ting.model.primitives.Category;

public interface IPinProvider {
	public void getPinsForCategory(Category category, PinRequest request, IPinReceivedCallback callback);
}
