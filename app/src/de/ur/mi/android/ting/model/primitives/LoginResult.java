package de.ur.mi.android.ting.model.primitives;

public class LoginResult {

	private boolean isRightLogin;
	private boolean hasGenericName;

	public LoginResult(boolean isRightLogin, boolean hasGenericName) {
		this.isRightLogin = isRightLogin;
		this.hasGenericName = hasGenericName;
	}
	
	public boolean getIsRightLogin(){
		return this.isRightLogin;
	}

	public boolean hasGenericName() {
		return this.hasGenericName;
	}

}
