package de.ur.mi.android.ting.model.dummy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.ur.mi.android.ting.app.controllers.BoardEditRequest;
import de.ur.mi.android.ting.model.IBoardsService;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.SearchRequest;
import de.ur.mi.android.ting.model.primitives.SearchResult;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public class DummyBoardsProvider implements IBoardsService {

	@Override
	public void getBoard(String boardId, final IDoneCallback<Board> callback) {
		DelayTask task = new DelayTask() {
			@Override
			protected void onPostExecute(Void result) {
				callback.done(new DummyBoard(100));
				super.onPostExecute(result);
			}
		};
		task.execute();
	}

	@Override
	public <T> void search(SearchRequest request,
			IDoneCallback<SearchResult<T>> callback) {
		// no need to do anything - generic search is implemented

	}

	@Override
	public void getUserBoards(String id,
			final IDoneCallback<Collection<Board>> callback) {
		DelayTask delayTask = new DelayTask() {
			@Override
			protected void onPostExecute(Void result) {
				Collection<Board> boards = new ArrayList<Board>();
				for (int i = 0; i < 10; i++) {
					DummyBoard dummyBoard = new DummyBoard(i);
					boards.add(dummyBoard);
				}
				callback.done(boards);
				super.onPostExecute(result);
			}
		};
		delayTask.execute();
	}

	@Override
	public void getLocalUserBoards(IDoneCallback<Collection<Board>> callback) {
		this.getUserBoards("someid", callback);
	}

	@Override
	public void saveBoard(BoardEditRequest request, IDoneCallback<Void> callback) {
		this.createBoard(request, callback);
	}

	@Override
	public void createBoard(BoardEditRequest request,
			final IDoneCallback<Void> callback) {
		DelayTask task = new DelayTask() {
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				callback.done(null);
			}
		};
		task.execute();
		
	}

}
