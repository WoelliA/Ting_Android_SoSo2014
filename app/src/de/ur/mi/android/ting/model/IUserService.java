package de.ur.mi.android.ting.model;

import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.model.primitives.User;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public interface IUserService extends ITypedSearchService<User> {
	public void login(String userName, String password,
			IDoneCallback<LoginResult> callback);

	public boolean checkIsLoggedIn();

	public void getUser(String userId,
			SimpleDoneCallback<User> simpleDoneCallback);
}
