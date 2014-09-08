package de.ur.mi.android.ting.app.controllers;

import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.utilities.IConnectivity;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public class LoginController {
	
	private IUserService userService;
	private IConnectivity connectivity;

	public LoginController(IUserService userService, IConnectivity connectivity) {
		this.userService = userService;
		this.connectivity = connectivity;
	}

	public void login(String userName, String password,
			IDoneCallback<LoginResult> callback) {
		if(!this.connectivity.hasWebAccess(true)){
			return;
		}
		this.userService.login(userName, password, callback);
		
	}

}
