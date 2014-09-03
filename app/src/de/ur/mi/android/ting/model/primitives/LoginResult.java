package de.ur.mi.android.ting.model.primitives;

public class LoginResult {

	private boolean isRightLogin;

	public LoginResult(boolean isRightLogin) {
		this.isRightLogin = isRightLogin;
	}
	
	public boolean getIsRightLogin(){
		return this.isRightLogin;
	}

}
