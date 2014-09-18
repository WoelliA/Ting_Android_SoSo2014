package de.ur.mi.android.ting.app.controllers;

import javax.inject.Inject;

import android.content.Context;
import android.content.Intent;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.activities.EditBoardActivity;
import de.ur.mi.android.ting.model.IBoardsService;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Board.BoardAffiliation;
import de.ur.mi.android.ting.utilities.IConnectivity;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;
import de.ur.mi.android.ting.utilities.view.Notify;

public class BoardController implements IBoardController {
	public static interface IBoardView {

		void showOwnerState();

		void showDefaultState(BoardAffiliation affiliation);

		void displayBoardInfo(Board result);

		Context getContext();
	}

	private LocalUser user;
	private IUserService userService;
	private IConnectivity connectivity;
	private IBoardView view;
	private IBoardsService boardsService;

	@Inject
	public BoardController(LocalUser user, IUserService userService, IConnectivity connectivity, IBoardsService boardsService) {
		this.user = user;
		this.userService = userService;
		this.connectivity = connectivity;
		this.boardsService = boardsService;
	}


	public void setup(IBoardView view, Board board) {
		this.view = view;
		view.displayBoardInfo(board);
		BoardAffiliation affiliation = board.getBoardAffiliation(this.user);
		if (affiliation == BoardAffiliation.Owner) {
			view.showOwnerState();
		} else {
			view.showDefaultState(affiliation);
		}
	}
	
	/* (non-Javadoc)
	 * @see de.ur.mi.android.ting.app.controllers.IBoardController#showEditBoard(android.content.Context)
	 */
	@Override
	public void showEditBoard(Context context, Board board){
		Intent intent = new Intent(context, EditBoardActivity.class);
		intent.putExtra(EditBoardActivity.BOARD_ID_KEY, board.getId());
		context.startActivity(intent);
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


	public void setup(final IBoardView view, String boardId) {
		this.boardsService.getBoard(boardId, new SimpleDoneCallback<Board>() {

			@Override
			public void done(Board result) {
				setup(view, result);				
			}
		});
		
	}
}
