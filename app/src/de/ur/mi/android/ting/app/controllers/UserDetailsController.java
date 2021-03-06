package de.ur.mi.android.ting.app.controllers;

import javax.inject.Inject;

import de.ur.mi.android.ting.app.activities.IUserDetailsView;
import de.ur.mi.android.ting.model.IBoardsService;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.User;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public class UserDetailsController extends UserBoardsController {

	private IUserService userService;
	private IUserDetailsView userDetailsView;
	protected Object user;

	@Inject
	public UserDetailsController(IUserService userService,
			IBoardsService boardsService, LocalUser user) {
		super(boardsService,user);
		this.userService = userService;
	}

	public void setView(IUserDetailsView userDetailsView, String userId) {
		super.init(userDetailsView, userId);
		this.userDetailsView = userDetailsView;
		this.initUserDetails();
	}

	private void initUserDetails() {
		this.userService.getUser(this.userId, new SimpleDoneCallback<User>() {

			@Override
			public void done(User result) {
				UserDetailsController.this.user = result;
				UserDetailsController.this.userDetailsView.setProfileInfo(result);
			}
		});

	}

}
