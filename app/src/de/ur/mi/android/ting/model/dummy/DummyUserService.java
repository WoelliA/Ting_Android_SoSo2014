package de.ur.mi.android.ting.model.dummy;

import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public class DummyUserService implements IUserService {

	private LocalUser user;
	boolean isRightLogin;
	private ICategoryProvider categoryProvider;

	public DummyUserService(LocalUser user, ICategoryProvider categoryProvider) {
		this.user = user;
		this.categoryProvider = categoryProvider;
	}

	@Override
	public void login(String userName, String password,
			final IDoneCallback<LoginResult> callback) {
		this.isRightLogin = userName.equals("right@right.de")
				&& password.equals("right");
		DelayTask loginTask = new DelayTask() {
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				DummyUserService.this.user
						.setIsLoggedIn(DummyUserService.this.isRightLogin);
				DummyUserService.this.user.setInfo("12355uuu",
						"Dummy Local User");
				callback.done(new LoginResult(
						DummyUserService.this.isRightLogin));
			}
		};
		loginTask.execute();
	}

	@Override
	public boolean checkIsLoggedIn() {
		this.user.setIsLoggedIn(DummyConfig.IS_USER_LOGGED_DEFAULT);
		return this.user.getIsLogedIn();
	}
}
