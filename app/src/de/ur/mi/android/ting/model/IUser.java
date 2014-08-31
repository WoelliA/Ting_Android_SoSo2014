package de.ur.mi.android.ting.model;

public interface IUser {
	public boolean getIsLogedIn();

	public void setIsLoggedIn(boolean isLoggedIn);

	public void setInfo(String id, String name);
	
	public String getId();
	
	public String getName();
}
