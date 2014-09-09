package de.ur.mi.android.ting.app.controllers;

import java.util.List;

import de.ur.mi.android.ting.app.fragments.ICollectionView;
import de.ur.mi.android.ting.model.IBoardsService;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public class UserBoardsController {
	private IBoardsService boardsService;
	private ICollectionView view;

	public UserBoardsController(IBoardsService boardsService) {
		this.boardsService = boardsService;
	}

	public void setView(ICollectionView view) {
		this.view = view;
		this.initBoards();
	}

	private void initBoards() {
		this.boardsService.getLocalUserBoards(new SimpleDoneCallback<List<Board>>() {

					@Override
					public void done(List<Board> result) {
						if (result != null) {
							UserBoardsController.this.view.addAll(result.toArray());
						}
					}
				});

	}
}
