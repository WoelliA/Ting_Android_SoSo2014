package de.ur.mi.android.ting.model.parse;

import com.parse.ParseObject;

import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.model.primitives.User;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public class ParseUserService implements IUserService {

	
	public User createUser(ParseObject parseObject){
		return null;
	}

	@Override
	public void login(String userName, String password,
			IDoneCallback<LoginResult> callback) {
		// TODO Auto-generated method stub
		
	}
}
