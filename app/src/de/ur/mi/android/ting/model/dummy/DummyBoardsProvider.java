package de.ur.mi.android.ting.model.dummy;

import java.util.ArrayList;
import java.util.List;

import de.ur.mi.android.ting.model.IBoardsProvider;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public class DummyBoardsProvider implements IBoardsProvider{

	@Override
	public void getUserBoards(String id,
			final SimpleDoneCallback<List<Board>> callback) {
		DelayTask delayTask = new DelayTask() {
			@Override
			protected void onPostExecute(Void result) {
				List<Board> boards = new ArrayList<Board>();
				for (int i = 0; i < 10; i++) {
					boards.add(new DummyBoard(i));
				}
				callback.done(boards);
				super.onPostExecute(result);
			}
		};
		delayTask.execute();
	}

	@Override
	public void getLocalUserBoards(
			SimpleDoneCallback<List<Board>> callback) {
		this.getUserBoards("someid", callback);
		
	}
	
}
