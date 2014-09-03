package de.ur.mi.android.ting.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.LoginResult;

@Singleton
public class LocalUser {

	private boolean isLoggedIn;
	private String name;
	private String id;
	private List<Category> favoriteCategories;
	private List<IChangeListener<LoginResult>> listeners;

	public LocalUser() {
		this.listeners = new ArrayList<IChangeListener<LoginResult>>();
	}

	public boolean getIsLogedIn() {
		return this.isLoggedIn;
	}

	public void setIsLoggedIn(boolean isLoggedIn) {
		LoginResult loginResult = new LoginResult(isLoggedIn);
		if(this.isLoggedIn != isLoggedIn){
			// only notify on change
			this.notifyLoginChangeListeners(loginResult);	
		}	

		this.isLoggedIn = isLoggedIn;
	}

	private void notifyLoginChangeListeners(LoginResult loginResult) {
		for (IChangeListener<LoginResult> iLoginChangeListener : this.listeners) {
			iLoginChangeListener.onChange(loginResult);
		}
	}

	public void setInfo(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setFavoriteCategories(List<Category> categories) {
		this.favoriteCategories = categories;
	}

	public List<Category> getFavoriteCategories() {
		return this.favoriteCategories;
	}

	public void addLoginChangeListener(IChangeListener<LoginResult> loginChangeListener) {
		this.listeners.add(loginChangeListener);
	}
}
