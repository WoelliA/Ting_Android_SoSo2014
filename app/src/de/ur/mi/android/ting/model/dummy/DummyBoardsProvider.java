package de.ur.mi.android.ting.model.dummy;

import java.util.ArrayList;
import java.util.Collection;

import de.ur.mi.android.ting.app.controllers.BoardEditRequest;
import de.ur.mi.android.ting.app.controllers.CategoryBoardsRequest;
import de.ur.mi.android.ting.model.IBoardsService;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.SearchRequest;
import de.ur.mi.android.ting.model.primitives.SearchResult;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public class DummyBoardsProvider implements IBoardsService {

	@Override
	public void getBoard(String boardId, final IDoneCallback<Board> callback) {
		DummyResultTask<Board> task = new DummyResultTask<Board>(
				new DummyBoard(100), callback);
	}

	@Override
	public <T> void search(SearchRequest request,
			IDoneCallback<SearchResult<T>> callback) {
		// no need to do anything - generic search is implemented

	}

	@Override
	public void getUserBoards(String id,
			final IDoneCallback<Collection<Board>> callback) {
		Collection<Board> boards = new ArrayList<Board>();
		for (int i = 0; i < 10; i++) {
			DummyBoard dummyBoard = new DummyBoard(i);
			boards.add(dummyBoard);
		}
		DummyResultTask<Collection<Board>> task = new DummyResultTask<Collection<Board>>(
				boards, callback);
	}

	@Override
	public void getLocalUserBoards(IDoneCallback<Collection<Board>> callback) {
		this.getUserBoards("someid", callback);
	}

	@Override
	public void saveBoard(BoardEditRequest request, IDoneCallback<Board> callback) {
		this.createBoard(request, callback);
	}

	@Override
	public void createBoard(BoardEditRequest request,
			final IDoneCallback<Board> callback) {
		DummyResultTask<Board> task = new DummyResultTask<Board>(new DummyBoard(1), callback);
	}

	@Override
	public void getBoardsForCategories(CategoryBoardsRequest request,
			IDoneCallback<Collection<Board>> pagingCallback) {;
		DummyResultTask<Collection<Board>> task = new DummyResultTask<Collection<Board>>(
				this.createDummyBoards(request.getOffset(), request.getCount()), pagingCallback);
		
	}

	private Collection<Board> createDummyBoards(int offset, int count) {
		Collection<Board> list= new ArrayList<Board>();
		for (int i = 0; i < count; i++) {
			list.add(new DummyBoard(i+offset));
		}
		return list;
	}

}
