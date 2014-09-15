package de.ur.mi.android.ting.app.controllers;

import javax.inject.Inject;

import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Board.BoardAffiliation;

public class BoardController {
	public static interface IBoardView {

		void showOwnerState();

		void showDefaultState(BoardAffiliation affiliation);
	}

	private LocalUser user;
	private IUserService userService;

	@Inject
	public BoardController(LocalUser user, IUserService userService) {
		this.user = user;
		this.userService = userService;
	}

	public void setup(IBoardView view, Board board) {
		BoardAffiliation affiliation = board.getBoardAffiliation(this.user);
		if (affiliation == BoardAffiliation.Owner) {
			view.showOwnerState();
		} else {
			view.showDefaultState(affiliation);
		}

	}

	public void setFollowBoard(Board board, boolean follow) {
		board.setAffilliation(BoardAffiliation.Follower);
		this.user.getFollowedBoards().add(board);
		this.userService.setFollowBoard(board.getId(),follow);
	}
}
