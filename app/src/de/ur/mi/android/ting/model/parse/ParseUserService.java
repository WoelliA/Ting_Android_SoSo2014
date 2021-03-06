package de.ur.mi.android.ting.model.parse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.app.Activity;
import android.text.TextUtils;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SignUpCallback;

import de.ur.mi.android.ting.app.controllers.EditProfileController.EditProfileResult;
import de.ur.mi.android.ting.app.fragments.RegisterRequest;
import de.ur.mi.android.ting.app.fragments.Service;
import de.ur.mi.android.ting.app.fragments.ServiceLoginResultType;
import de.ur.mi.android.ting.model.IBoardsService;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.parse.callbacks.SaveCallbackWrap;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.model.primitives.SearchRequest;
import de.ur.mi.android.ting.model.primitives.SearchResult;
import de.ur.mi.android.ting.model.primitives.User;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public class ParseUserService implements IUserService {

	private LocalUser localuser;

	public ParseUserService(LocalUser user) {
		this.localuser = user;
	}

	@Override
	public void login(String userName, String password,
			final IDoneCallback<LoginResult> callback) {

		ParseUser.logInInBackground(userName, password, new LogInCallback() {
			@Override
			public void done(ParseUser u, ParseException e) {
				boolean isSuccess = u != null;
				LoginResult lr = new LoginResult(isSuccess, false);
				if (isSuccess) {
					ParseUserService.this.setUserInfo(u, false, false);
				}
				callback.done(lr);
			}
		});
	}

	protected void setUserInfo(ParseUser u, boolean isNew, boolean hasGenericName) {
		this.localuser.setInfo(ParseHelper.createUser(u), u.getEmail());
		if (!isNew) {
			this.tryAddBoardsFollowed(u);
			this.tryAddLikedPins(u);
			this.tryAddOwnedBoards();
		}
		ParseUserService.this.localuser.setIsLoggedIn(true, hasGenericName);
	}

	private void tryAddOwnedBoards() {
		IBoardsService boardsService = new ParseBoardsService(this.localuser);
		boardsService
				.getLocalUserBoards(new SimpleDoneCallback<Collection<Board>>() {

					@Override
					public void done(Collection<Board> result) {
						ParseUserService.this.localuser.setOwnedBoards(result);
					}
				});
	}

	private void tryAddLikedPins(ParseUser u) {
		if (u.has("liked_pins")) {
			ParseRelation<ParseObject> likedPins = u.getRelation("liked_pins");
			ParseQuery<ParseObject> query = likedPins.getQuery();
			query.selectKeys(new ArrayList<String>()); // only get the ID
			query.findInBackground(new FindCallback<ParseObject>() {

				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					if (objects == null || e != null) {
						return;
					}
					Collection<Pin> likedPins = ParseHelper.createPins(objects);
					ParseUserService.this.localuser.setLikedPins(likedPins);
				}
			});
		}

	}

	private void tryAddBoardsFollowed(ParseUser u) {
		if (u.has("boards_followed")) {

			ParseRelation<ParseObject> followedBoards = u
					.getRelation("boards_followed");
			ParseQuery<ParseObject> query = followedBoards.getQuery();
			query.selectKeys(new ArrayList<String>()); // only get the ID
			query.findInBackground(new FindCallback<ParseObject>() {

				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					if (objects == null || e != null) {
						return;
					}
					List<Board> boards = new ArrayList<Board>();
					for (ParseObject object : objects) {
						boards.add(ParseHelper.createBoard(object));
					}
					ParseUserService.this.localuser.setFollowedBoards(boards);
				}
			});

		}
	}

	@Override
	public boolean checkIsLoggedIn() {
		ParseUser user = ParseUser.getCurrentUser();
		boolean isLoggedIn = user != null;
		if (isLoggedIn) {
			this.setUserInfo(user, false, false);
		}
		return isLoggedIn;
	}

	@Override
	public void getUser(String userId, final SimpleDoneCallback<User> callback) {
		if (!ParseCache.current().restore(userId,
				new GetCallback<ParseObject>() {

					@Override
					public void done(ParseObject object, ParseException e) {
						callback.done(ParseHelper.createUser(object));
					}
				})) {
			ParseQuery<ParseUser> query = ParseUser.getQuery();
			query.getInBackground(userId, new GetCallback<ParseUser>() {

				@Override
				public void done(ParseUser object, ParseException e) {
					callback.done(ParseHelper.createUser(object));
				}
			});
		}
	}

	@Override
	public <T> void search(final SearchRequest request,
			final IDoneCallback<SearchResult<T>> callback) {
		String[] searchedFields = new String[] { "username" };
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereMatches("username", request.getQuery(), "mi");

		query.findInBackground(new FindCallback<ParseUser>() {

			@SuppressWarnings("unchecked")
			@Override
			public void done(List<ParseUser> objects, ParseException e) {
				if (e == null && objects != null) {
					List<? extends ParseObject> bases = objects;
					callback.done((SearchResult<T>) new SearchResult<User>(
							ParseHelper.createUsers(bases), request.getCount()));
				}
			}
		});

	}

	@Override
	public void saveChangedUser(EditProfileResult editProfileResult,
			IDoneCallback<Void> callback) {
		final ParseUser user = ParseUser.getCurrentUser();
		String email = editProfileResult.getEmail();
		if(email != null && !TextUtils.isEmpty(email)){
			user.setEmail(editProfileResult.getEmail());
		}
		user.setUsername(editProfileResult.getName());
		user.put("info", editProfileResult.getInfo());

		if (editProfileResult.getNewProfileImage() != null) {
			ParseFile file = new ParseFile(
					editProfileResult.getNewProfileImage());
			user.put("profile_picture", file);
		}

		user.saveInBackground(new SaveCallbackWrap(callback) {
			@Override
			public void done(ParseException e) {
				super.done(e);
				ParseUserService.this.localuser.setInfo(
						ParseHelper.createUser(user), user.getEmail());
			}
		});
	}

	@Override
	public void setPinLike(Pin pin, boolean isliked) {
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser == null) {
			return;
		}
		ParseRelation<ParseObject> likedPins = currentUser
				.getRelation("liked_pins");
		if (isliked) {
			likedPins.add(ParseObject.createWithoutData("pin", pin.getId()));
		} else {
			likedPins.remove(ParseObject.createWithoutData("pin", pin.getId()));
		}
		currentUser.saveEventually();
	}

	@Override
	public void logout() {
		ParseUser.logOut();
	}

	@Override
	public void setIsFavoriteCategory(Category category, boolean isFavorite) {
		ParseUser user = ParseUser.getCurrentUser();
		if (user == null) {
			return;
		}
		user.getRelation("favorite_categories").add(
				ParseObject.createWithoutData("category", category.getId()));
		user.saveEventually();
	}

	@Override
	public void getFavoriteCategories(
			final IDoneCallback<List<Category>> callback) {
		ParseUser user = ParseUser.getCurrentUser();
		if (user == null) {
			callback.fail(new Exception("not logged in"));
			return;
		}
		user.getRelation("favorite_categories").getQuery()
				.findInBackground(new FindCallback<ParseObject>() {

					@Override
					public void done(List<ParseObject> objects, ParseException e) {
						if (e != null || objects == null) {
							callback.fail(e);
						} else {
							callback.done(ParseHelper.createCategories(objects));
						}
					}
				});
	}

	@Override
	public void setFollowBoard(String boardId, boolean follow) {
		ParseUser user = ParseUser.getCurrentUser();
		if (user == null) {
			return;
		}
		user.getRelation("boards_followed").add(
				ParseObject.createWithoutData("board", boardId));
		user.saveEventually();
	}

	@Override
	public void register(RegisterRequest request,
			final IDoneCallback<Boolean> callback) {
		final ParseUser user = new ParseUser();
		user.setUsername(request.getName());
		user.setPassword(request.getPassword());
		String email = request.getEmail();
		if(!TextUtils.isEmpty(email)){
			user.setEmail(email);			
		}

		if (request.getGender() != null && request.getGender().getId() != null) {
			user.put("gender", ParseObject.createWithoutData("gender", request
					.getGender().getId()));
		}
		user.signUpInBackground(new SignUpCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					ParseUserService.this.setUserInfo(user, true, false);
					callback.done(true);
				} else {
					callback.fail(e);
				}
			}
		});
	}

	@Override
	public void loginThirdParty(Service service, Activity activity,
			final IDoneCallback<ServiceLoginResultType> callback) {
		LogInCallback logInCallback = new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					boolean isnew = user.isNew();
					ParseUserService.this.setUserInfo(user, isnew, true);
					ServiceLoginResultType type = isnew ? ServiceLoginResultType.Register
							: ServiceLoginResultType.Login;
					callback.done(type);
				} else {
					callback.fail(null);
				}
			}
		};

		switch (service) {
		case Facebook:
			ParseFacebookUtils.logIn(activity, logInCallback);
			break;
		case Twitter:
			ParseTwitterUtils.logIn(activity, logInCallback);
		}

	}

	@Override
	public void restorePassword(String email, final IDoneCallback<Void> callback) {

		ParseUser.requestPasswordResetInBackground(email,
				new RequestPasswordResetCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null) {
							callback.done(null);
						} else {
							callback.fail(e);
						}

					}
				});
	}

	@Override
	public void saveFavoriteCategories(Collection<String> ids) {
		ParseUser user = ParseUser.getCurrentUser();
		if(user == null || ids == null){
			return;
		}
		ParseRelation<ParseObject> relation = user.getRelation("favorite_categories");
		for (String id : ids) {
			relation.add(ParseObject.createWithoutData("category", id));
		}
		user.saveEventually();
	}

}
