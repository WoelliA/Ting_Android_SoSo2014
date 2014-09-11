package de.ur.mi.android.ting.model;

import java.util.Collection;
import java.util.List;

import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public interface IBoardsService extends ITypedSearchService<Board>{

	void getUserBoards(String id, IDoneCallback<Collection<Board>> callback);

	void getLocalUserBoards(IDoneCallback<Collection<Board>> callback);

	void getBoard(
			String boardId,
			IDoneCallback<Board> callback);

}
