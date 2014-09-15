package de.ur.mi.android.ting.model;

import java.util.Collection;
import de.ur.mi.android.ting.app.controllers.BoardEditRequest;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public interface IBoardsService extends ITypedSearchService<Board>{

	void getUserBoards(String id, IDoneCallback<Collection<Board>> callback);

	void getLocalUserBoards(IDoneCallback<Collection<Board>> callback);

	void getBoard(
			String boardId,
			IDoneCallback<Board> callback);

	void createBoard(BoardEditRequest request, IDoneCallback<Void> callback);

	void saveBoard(BoardEditRequest request,
			IDoneCallback<Void> callback);

}
