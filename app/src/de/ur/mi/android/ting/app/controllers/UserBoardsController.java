package de.ur.mi.android.ting.app.controllers;

import java.util.Collection;

import javax.inject.Inject;

import de.ur.mi.android.ting.app.fragments.ICollectionView;
import de.ur.mi.android.ting.model.IBoardsService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public class UserBoardsController {
	private IBoardsService boardsService;
	private ICollectionView view;
	protected String userId;
	private LocalUser user;

	@Inject
	public UserBoardsController(IBoardsService boardsService, LocalUser user) {
		this.boardsService = boardsService;
		this.user = user;
	}

	public void init(ICollectionView view, String userId) {
		this.view = view;
		this.userId = userId;
		this.initBoards();
	}

	private void initBoards() {
		if(this.user.getId() == this.userId){
			this.view.set(this.user.getOwnedBoards().toArray());
			return;
		}
		
		this.boardsService.getUserBoards(this.userId,
				new SimpleDoneCallback<Collection<Board>>() {

					@Override
					public void done(Collection<Board> result) {
						if (result != null) {
							UserBoardsController.this.view.set(result.toArray());
						}
					}
				});

	}
}
