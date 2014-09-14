package de.ur.mi.android.ting.app.controllers;

import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.utilities.IConnectivity;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.view.Notify;

public class LoginController implements IChangeListener<LoginResult> {

	private IUserService userService;
	private IConnectivity connectivity;
	private LocalUser user;

	public LoginController(IUserService userService,
			IConnectivity connectivity, LocalUser user) {
		this.userService = userService;
		this.connectivity = connectivity;
		this.user = user;
		this.user.addLoginChangeListener(this);
	}

	public void login(String userName, String password,
			IDoneCallback<LoginResult> callback) {
		if (!this.connectivity.hasWebAccess(true)) {
			return; 
		}
		this.userService.login(userName, password, callback);
	}

	public boolean checkIsLoggedIn() {
		return userService.checkIsLoggedIn();
	}

	@Override
	public void onChange(LoginResult loginres) {
		if (loginres.getIsRightLogin()) {
			Notify.current().showToast("Welcome " + user.getName());
		}
	}

	public void logout() {
		this.userService.logout();
		user.setIsLoggedIn(false);
	}
}
