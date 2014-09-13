package de.ur.mi.android.ting.model.dummy;

import de.ur.mi.android.ting.app.controllers.EditProfileController.EditProfileResult;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.model.primitives.SearchRequest;
import de.ur.mi.android.ting.model.primitives.SearchResult;
import de.ur.mi.android.ting.model.primitives.User;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

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
				DummyUserService.this.user.setInfo(new DummyUser(1), "w@w.de");
				callback.done(new LoginResult(
						DummyUserService.this.isRightLogin));
			}
		};
		loginTask.execute();
	}

	@Override
	public boolean checkIsLoggedIn() {
		this.user.setIsLoggedIn(DummyConfig.IS_USER_LOGGED_DEFAULT);
		this.user.setInfo(new DummyUser(1), "w@w.de");
		return this.user.getIsLogedIn();
	}

	@Override
	public void getUser(String userId, final SimpleDoneCallback<User> callback) {
		DelayTask task = new DelayTask() {
			@Override
			protected void onPostExecute(Void result) {
				callback.done(new DummyUser(10));
				super.onPostExecute(result);
			}
		};
		task.execute();
	}

	@Override
	public <T> void search(SearchRequest request,
			IDoneCallback<SearchResult<T>> callback) {
		// no need to do anything - generic search is implemented

	}

	@Override
	public void saveChangedUser(EditProfileResult editProfileResult,
			IDoneCallback<Void> callback) {
		new DummyResultTask<Void>(null, callback);

	}

}
