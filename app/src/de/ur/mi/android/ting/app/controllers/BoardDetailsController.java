package de.ur.mi.android.ting.app.controllers;

import java.util.Collection;
import java.util.List;

import de.ur.mi.android.ting.model.IBoardsService;
import de.ur.mi.android.ting.model.IPaging;
import de.ur.mi.android.ting.model.IPinService;
import de.ur.mi.android.ting.model.PagingResult;
import de.ur.mi.android.ting.model.PinRequest;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public class BoardDetailsController extends PagingController<Pin> implements
		IPaging<Pin> {
	private IBoardDetailsView view;
	
	private IBoardsService boardsService;
	private IPinService pinService;
	
	private String boardId;

	public BoardDetailsController(IPinService pinService, IBoardsService boardsService) {
		this.pinService = pinService;
		this.boardsService = boardsService;
	}

	public void init(final IBoardDetailsView view, String boardId) {
		this.view = view;
		this.boardId = boardId;
		
		this.boardsService.getBoard(this.boardId, new SimpleDoneCallback<Board>(){

			@Override
			public void done(Board result) {
				view.displayBoardInfo(result);				
			}			
		});
	}

	@Override
	protected void loadNextPage(int offset,
			final IDoneCallback<PagingResult<Pin>> doneCallback) {
		if(boardId == null)
			return;
		int pagingSize = 10;
		final PinRequest pinRequest = new PinRequest(offset, pagingSize);
		this.pinService.getPinsForBoard(this.boardId, pinRequest,
				new SimpleDoneCallback<Collection<Pin>>() {
					@Override
					public void done(Collection<Pin> result) {
						doneCallback.done(new PagingResult<Pin>(pinRequest
								.getCount(), result));
					}
				});
	}

	public interface IBoardDetailsView {
		void displayBoardInfo(Board result);
	}
}
