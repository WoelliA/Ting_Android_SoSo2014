package de.ur.mi.android.ting.model.parse;

import java.util.List;

import de.ur.mi.android.ting.model.IBoardsService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public class ParseBoardsProvider implements IBoardsService {

	private LocalUser user;

	public ParseBoardsProvider(LocalUser user) {
		this.user = user;
	}

	@Override
	public void getUserBoards(String id,
			SimpleDoneCallback<List<Board>> callback) {
		// TODO implement getUserBoards
		
	}

	@Override
	public void getLocalUserBoards(
			SimpleDoneCallback<List<Board>> callback) {
		this.getUserBoards(this.user.getId(), callback);		
	}

	@Override
	public void getBoard(String boardId, IDoneCallback<Board> callback) {
		// TODO get board details implement - use weakreference to cache results - or build custom cache
		
	}

	

}
