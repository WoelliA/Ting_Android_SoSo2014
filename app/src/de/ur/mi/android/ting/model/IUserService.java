package de.ur.mi.android.ting.model;

import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public interface IUserService {
	public void login(String userName, String password, IDoneCallback<LoginResult> callback);

	public boolean checkIsLoggedIn();
}
