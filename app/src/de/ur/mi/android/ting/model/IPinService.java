package de.ur.mi.android.ting.model;

import java.util.Collection;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public interface IPinService extends ITypedSearchService<Pin> {
	public void getPinsForCategory(Category category, PinRequest request,
			IPinReceivedCallback callback);

	public void createPin(PinData pin, String sharedPinId, Board selectedBoard,
			IDoneCallback<Void> callback);

	public void getPinsForBoard(String boardId, PinRequest request,
			IDoneCallback<Collection<Pin>> callback);

	public void getPin(
			String pinId,
			IDoneCallback<Pin> callback);
}
