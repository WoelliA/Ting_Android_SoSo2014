package de.ur.mi.android.ting.app.controllers;

public class FriendsController {
	
	public interface IFriendsView{
		
	}

	private IFriendsView view;

	public void setView(IFriendsView friendsView) {
		this.view = friendsView;		
	}

}
