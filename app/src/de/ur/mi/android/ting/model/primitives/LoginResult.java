package de.ur.mi.android.ting.model.primitives;

public class LoginResult {

	private boolean isRightLogin;
	private boolean isNew;

	public LoginResult(boolean isRightLogin, boolean isNew) {
		this.isRightLogin = isRightLogin;
		this.isNew = isNew;
	}
	
	public boolean getIsRightLogin(){
		return this.isRightLogin;
	}

	public boolean isNew() {
		return this.isNew;
	}

}
