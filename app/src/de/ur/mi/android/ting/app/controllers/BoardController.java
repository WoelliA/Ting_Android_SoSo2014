package de.ur.mi.android.ting.app.controllers;

import javax.inject.Inject;

import android.content.Context;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Board.BoardAffiliation;
import de.ur.mi.android.ting.utilities.IConnectivity;
import de.ur.mi.android.ting.utilities.view.Notify;

public class BoardController {
	public static interface IBoardView {

		void showOwnerState();

		void showDefaultState(BoardAffiliation affiliation);

		Context getContext();
	}

	private LocalUser user;
	private IUserService userService;
	private IConnectivity connectivity;
	private IBoardView view;

	@Inject
	public BoardController(LocalUser user, IUserService userService, IConnectivity connectivity) {
		this.user = user;
		this.userService = userService;
		this.connectivity = connectivity;
	}

	public void setup(IBoardView view, Board board) {
		this.view = view;
		BoardAffiliation affiliation = board.getBoardAffiliation(this.user);
		if (affiliation == BoardAffiliation.Owner) {
			view.showOwnerState();
		} else {
			view.showDefaultState(affiliation);
		}

	}

	public boolean setFollowBoard(Board board, boolean follow) {
		if(!connectivity.hasWebAccess(true)){
			return false;
		}
		if(!this.user.getIsLogedIn()){
			new NotifyRequiresLogin(view.getContext(), R.string.needs_login_followboard).show();
			return false;
		}
		board.setAffilliation(BoardAffiliation.Follower);
		this.user.getFollowedBoards().add(board);
		this.userService.setFollowBoard(board.getId(),follow);
		return true;
	}
}
