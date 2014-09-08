package de.ur.mi.android.ting.model;

import java.util.List;

import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public interface IBoardsProvider {

	void getUserBoards(String id, SimpleDoneCallback<List<Board>> callback);

	void getLocalUserBoards(SimpleDoneCallback<List<Board>> callback);

}
