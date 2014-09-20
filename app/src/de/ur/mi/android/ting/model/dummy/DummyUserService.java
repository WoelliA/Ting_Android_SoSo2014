package de.ur.mi.android.ting.model.dummy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.parse.ParseUser;

import android.app.Activity;
import de.ur.mi.android.ting.app.controllers.EditProfileController.EditProfileResult;
import de.ur.mi.android.ting.app.fragments.RegisterRequest;
import de.ur.mi.android.ting.app.fragments.Service;
import de.ur.mi.android.ting.app.fragments.ServiceLoginResultType;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.model.primitives.SearchRequest;
import de.ur.mi.android.ting.model.primitives.SearchResult;
import de.ur.mi.android.ting.model.primitives.User;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public class DummyUserService implements IUserService {

	private LocalUser user;
	boolean isRightLogin;

	public DummyUserService(LocalUser user) {
		this.user = user;
	}

	@Override
	public void login(String userName, String password,
			final IDoneCallback<LoginResult> callback) {
		this.isRightLogin = userName.equals("right@right.de")
				&& password.equals("right");
		DelayTask loginTask = new DelayTask(DummyConfig.DUMMY_SIMULATED_NETWORK_DELAY_inmilliseconds) {
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				DummyUserService.this.user
						.setIsLoggedIn(DummyUserService.this.isRightLogin, false);
				DummyUserService.this.user.setInfo(new DummyUser(1), "w@w.de");
				callback.done(new LoginResult(
						DummyUserService.this.isRightLogin, false));
			}
		};
		loginTask.execute();
	}

	@Override
	public boolean checkIsLoggedIn() {
		this.user.setIsLoggedIn(DummyConfig.IS_USER_LOGGED_DEFAULT, false);
		this.user.setInfo(new DummyUser(1), "w@w.de");
		return this.user.getIsLogedIn();
	}

	@Override
	public void getUser(String userId, final SimpleDoneCallback<User> callback) {
		DelayTask task = new DelayTask(DummyConfig.DUMMY_SIMULATED_NETWORK_DELAY_inmilliseconds) {
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

	@Override
	public void setPinLike(Pin pin, boolean isliked) {
	}

	@Override
	public void logout() {
	}

	@Override
	public void setIsFavoriteCategory(Category category, boolean isFavorite) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getFavoriteCategories(
			final IDoneCallback<List<Category>> callback) {

		DelayTask task = new DelayTask(DummyConfig.DUMMY_SIMULATED_NETWORK_DELAY_inmilliseconds) {
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				Random random = new Random();
				List<Category> favorites = new ArrayList<Category>();
				// for (Category category : DummyCategoryProvider.super
				// .getCategories()) {
				// int r = random.nextInt();
				// if (r % 4 == 0) {
				// favorites.add(category);
				// }
				// }
				callback.done(favorites);
			}
		};
		task.execute();

	}

	@Override
	public void setFollowBoard(String boardId, boolean follow) {

	}

	@Override
	public void register(RegisterRequest registerRequest,
			IDoneCallback<Boolean> callback) {
		DummyResultTask<Boolean> task = new DummyResultTask<Boolean>(true,
				callback);

	}

	@Override
	public void loginThirdParty(Service service, Activity activity,
			IDoneCallback<ServiceLoginResultType> simpleDoneCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public void restorePassword(String email, IDoneCallback<Void> callback) {
		DummyResultTask<Void> task = new DummyResultTask<Void>(null,
				callback);
		
	}

	@Override
	public void saveFavoriteCategories(Collection<String> ids) {

	}

}
