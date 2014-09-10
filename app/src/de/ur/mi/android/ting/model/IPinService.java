package de.ur.mi.android.ting.model;

import java.util.List;

import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public interface IPinService {
	public void getPinsForCategory(Category category, PinRequest request,
			IPinReceivedCallback callback);

	public void createPin(PinData result, Board selectedBoard,
			IDoneCallback<Void> callback);

	public void getPinsForBoard(String boardId, PinRequest request,
			IDoneCallback<List<Pin>> simpleDoneCallback);
}
