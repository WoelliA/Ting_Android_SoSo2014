package de.ur.mi.android.ting.model.primitives;

import java.util.List;


public class Board extends UniqueBase implements IHasCategory{
	private String title;
	private String description;
	protected List<User> contributors;
	private Category category;
	private User owner;
	private boolean isFollowing;

	public Board(String id, Category category, String title, String description, User owner, List<User> contributors) {
		super(id);
		this.category = category;
		this.title = title;
		this.description = description;
		this.owner =owner;
		this.contributors = contributors;
	}

	@Override
	public Category getCategory() {
		return this.category;
	}
	
	public List<User> getContributors(){
		return this.contributors;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getDescription(){
		return this.description;
	}

	public User getOwner() {
		return this.owner;
	}
	
	public void setIsUserFollowing(boolean isFollowing){
		this.isFollowing = isFollowing;
	}

	public boolean getIsUserFollowing() {
		return this.isFollowing;
	}

}
