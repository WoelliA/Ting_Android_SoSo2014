package de.ur.mi.android.ting.model.dummy;

import de.ur.mi.android.ting.model.IUser;

public class DummyLocalUser implements IUser {

	private boolean isloggedIn;
	public DummyLocalUser(boolean isloggedIn) {
		this.isloggedIn = isloggedIn;
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean getIsLogedIn() {
		return isloggedIn;

	}

}
