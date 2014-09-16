package de.ur.mi.android.ting.model;

import java.util.List;

import android.app.Activity;
import de.ur.mi.android.ting.app.controllers.EditProfileController.EditProfileResult;
import de.ur.mi.android.ting.app.fragments.RegisterRequest;
import de.ur.mi.android.ting.app.fragments.Service;
import de.ur.mi.android.ting.app.fragments.ServiceLoginResultType;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.model.primitives.User;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public interface IUserService extends ITypedSearchService<User> {
	public void login(String userName, String password,
			IDoneCallback<LoginResult> callback);

	public boolean checkIsLoggedIn();

	public void getUser(String userId,
			SimpleDoneCallback<User> simpleDoneCallback);

	public void saveChangedUser(EditProfileResult editProfileResult,
			IDoneCallback<Void> callback);

	public void setPinLike(Pin pin, boolean isliked);

	public void logout();

	public void setIsFavoriteCategory(Category category, boolean isFavorite);

	public void getFavoriteCategories(IDoneCallback<List<Category>> callback);
 
	public void setFollowBoard(String boardId, boolean follow);

	public void register(RegisterRequest registerRequest,
			IDoneCallback<Boolean> callback);
	
	public void loginThirdParty(
			Service service,
			Activity activity,
			IDoneCallback<ServiceLoginResultType> simpleDoneCallback);

}
				