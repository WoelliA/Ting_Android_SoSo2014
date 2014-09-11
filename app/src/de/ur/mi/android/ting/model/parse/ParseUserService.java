package de.ur.mi.android.ting.model.parse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.model.primitives.SearchRequest;
import de.ur.mi.android.ting.model.primitives.SearchResult;
import de.ur.mi.android.ting.model.primitives.User;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public class ParseUserService implements IUserService {

	private LocalUser user;

	public ParseUserService(LocalUser user) {
		this.user = user;
	}

	@Override
	public void login(String userName, String password,
			final IDoneCallback<LoginResult> callback) {

		ParseUser.logInInBackground(userName, password, new LogInCallback() {

			@Override
			public void done(ParseUser u, ParseException e) {
				boolean isSuccess = u != null;
				LoginResult lr = new LoginResult(isSuccess);
				ParseUserService.this.user.setIsLoggedIn(isSuccess);
				if (isSuccess) {
					ParseUserService.this.user.setInfo(u.getObjectId(),
							u.getUsername());
				}
				callback.done(lr);
			}
		});
	}

	@Override
	public boolean checkIsLoggedIn() {
		ParseUser user = ParseUser.getCurrentUser();
		boolean isLoggedIn = user != null;
		if (isLoggedIn) {
			this.user.setInfo(user.getObjectId(), user.getUsername());
		}
		this.user.setIsLoggedIn(isLoggedIn);
		return isLoggedIn;
	}

	@Override
	public void getUser(String userId,
			final SimpleDoneCallback<User> callback) {
		if(!ParseCache.current().restore(userId, new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject object, ParseException e) {
				callback.done(ParseHelper.createUser(object));				
			}
		})){
			
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
							ParseHelper.createUsers(bases), request
									.getCount()));
				}

			}
		});

	}

}
