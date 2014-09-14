package de.ur.mi.android.ting.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.inject.Singleton;

import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.model.primitives.UniqueBase;
import de.ur.mi.android.ting.model.primitives.User;

@Singleton
public class LocalUser extends User {

	private boolean isLoggedIn;
	private List<Category> favoriteCategories;
	private List<IChangeListener<LoginResult>> listeners;
	private String email;
	private List<Board> followedBoards = new ArrayList<Board>();
	private HashSet<Pin> likedPins = new HashSet<Pin>();
	private static LocalUser current;

	public LocalUser() {
		super(null);
		current = this;
		this.listeners = new ArrayList<IChangeListener<LoginResult>>();
	}

	public boolean getIsLogedIn() {
		return this.isLoggedIn;
	}

	public void setIsLoggedIn(boolean isLoggedIn) {
		LoginResult loginResult = new LoginResult(isLoggedIn);
		if (this.isLoggedIn != isLoggedIn) {
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

	public void setInfo(User user, String email) {
		this.id = user.getId();
		this.name = user.getName();
		this.setInfo(user.getInfo());
		this.setProfilePictureUrl(user.getProfilePictureUri());
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	public void setFavoriteCategories(List<Category> categories) {
		this.favoriteCategories = categories;
	}

	public List<Category> getFavoriteCategories() {
		return this.favoriteCategories;
	}

	public void addLoginChangeListener(
			IChangeListener<LoginResult> loginChangeListener) {
		this.listeners.add(loginChangeListener);
	}

	public static LocalUser current() {
		return current;
	}

	public List<Board> getFollowedBoards() {
		return this.followedBoards;
	}

	public void setFollowedBoards(Collection<Board> boards) {
		this.followedBoards = new ArrayList<Board>(boards);
	}

	public void setLikedPins(Collection<Pin> likedPins) {
		this.likedPins = new HashSet<Pin>(likedPins);
	}
	
	public HashSet<Pin> getLikedPins(){
		return this.likedPins;
	}
}
