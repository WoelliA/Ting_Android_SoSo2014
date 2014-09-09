package de.ur.mi.android.ting.model;

import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public interface IPinProvider {
	public void getPinsForCategory(Category category, PinRequest request, IPinReceivedCallback callback);

	public void createPin(PinData result, Board selectedBoard, IDoneCallback<Void> callback);
}
