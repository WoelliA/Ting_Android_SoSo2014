package de.ur.mi.android.ting.model;

import javax.inject.Singleton;

@Singleton
public class LocalUser implements IUser {

	private boolean isLoggedIn;
	private String name;
	private String id;
	
	public LocalUser() {
		
	}

	@Override
	public boolean getIsLogedIn() {
		return this.isLoggedIn;
	}

	@Override
	public void setIsLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	@Override
	public void setInfo(String id, String name) {
		this.id = id;
		this.name = name;		
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	
}
