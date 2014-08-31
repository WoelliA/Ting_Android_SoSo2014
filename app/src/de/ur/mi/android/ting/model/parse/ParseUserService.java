package de.ur.mi.android.ting.model.parse;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import de.ur.mi.android.ting.model.IUser;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.model.primitives.User;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public class ParseUserService implements IUserService {

	private IUser user;

	public ParseUserService(IUser user) {
		this.user = user;
		
	}
	public User createUser(ParseObject parseObject){
		return null;
	}

	@Override
	public void login(String userName, String password,
			final IDoneCallback<LoginResult> callback) {
		
		ParseUser.logInInBackground(userName, password, new LogInCallback() {
			
			@Override
			public void done(ParseUser u, ParseException e) {	
				boolean isSuccess = u != null;
				LoginResult lr = new LoginResult(isSuccess);	
				user.setIsLoggedIn(isSuccess);
				if(isSuccess)
				{
					user.setInfo(u.getObjectId(), u.getUsername());
				}
				callback.done(lr);
			}		
		});
	}
	@Override
	public boolean checkIsLoggedIn() {
		 ParseUser user = ParseUser.getCurrentUser();
		 boolean isLoggedIn = user!= null;
		 if(isLoggedIn)
			 this.user.setInfo(user.getObjectId(), user.getUsername());
		 this.user.setIsLoggedIn(isLoggedIn);
		 return isLoggedIn;
	}
	
}
