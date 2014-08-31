package de.ur.mi.android.ting.model.primitives;

public class LoginResult {

	private boolean isRightLogin;

	public LoginResult(boolean isRightLogin) {
		this.isRightLogin = isRightLogin;
		// TODO Auto-generated constructor stub
	}
	
	public boolean getIsRightLogin(){
		return isRightLogin;
	}
}
