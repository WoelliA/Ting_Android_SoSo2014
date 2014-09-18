package de.ur.mi.android.ting.app.controllers;

import java.util.Collection;
import javax.inject.Inject;

import de.ur.mi.android.ting.app.controllers.BoardController.IBoardView;
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

	
	public static interface IBoardDetailsView extends IBoardView {
	}

	private IBoardsService boardsService;
	private IPinService pinService;

	private String boardId;
	private Board board;

	private BoardController boardcontroller;

	@Inject
	public BoardDetailsController(BoardController boardcontroller, IPinService pinService) {
		this.boardcontroller = boardcontroller;
		this.pinService = pinService;
	}

	public void init(final IBoardDetailsView view, String boardId) {
		this.boardId = boardId;
		this.boardcontroller.setup(view, boardId);
	}

	@Override
	protected void loadNextPage(int offset,
			final IDoneCallback<PagingResult<Pin>> doneCallback) {
		if (this.boardId == null) {
			return;
		}
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

	public void setFollowBoard(boolean follow) {
		this.boardcontroller.setFollowBoard(this.board, follow);
	}
}
