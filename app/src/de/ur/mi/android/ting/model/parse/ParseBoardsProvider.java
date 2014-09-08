package de.ur.mi.android.ting.model.parse;

import java.util.List;

import de.ur.mi.android.ting.model.IBoardsProvider;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public class ParseBoardsProvider implements IBoardsProvider {

	private LocalUser user;

	public ParseBoardsProvider(LocalUser user) {
		this.user = user;
	}

	@Override
	public void getUserBoards(String id,
			SimpleDoneCallback<List<Board>> callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getLocalUserBoards(
			SimpleDoneCallback<List<Board>> callback) {
		this.getUserBoards(this.user.getId(), callback);
		
	}

	

}
