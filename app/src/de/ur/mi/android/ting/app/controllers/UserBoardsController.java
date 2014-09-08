package de.ur.mi.android.ting.app.controllers;

import java.util.List;

import de.ur.mi.android.ting.app.ISelectedListener;
import de.ur.mi.android.ting.app.fragments.ICollectionView;
import de.ur.mi.android.ting.model.IBoardsProvider;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public class UserBoardsController {
	private IBoardsProvider boardsProvider;
	private ICollectionView view;

	public UserBoardsController(IBoardsProvider boardsProvider) {
		this.boardsProvider = boardsProvider;
	}

	public void setView(ICollectionView view) {
		this.view = view;
		this.initBoards();
	}

	private void initBoards() {
		this.boardsProvider.getLocalUserBoards(new SimpleDoneCallback<List<Board>>() {

					@Override
					public void done(List<Board> result) {
						if (result != null) {
							view.addAll(result.toArray());
						}
					}
				});

	}
}
