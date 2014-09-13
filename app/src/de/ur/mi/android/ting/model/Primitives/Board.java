package de.ur.mi.android.ting.model.primitives;

import java.util.List;

import de.ur.mi.android.ting.model.LocalUser;

public class Board extends UniqueBase implements IHasCategory {
	private String title;
	private String description;
	protected List<User> contributors;
	private Category category;
	private User owner;
	private boolean isFollowing;

	BoardAffiliation affiliation;

	protected Board(String id, Category category, String title,
			String description, User owner, List<User> contributors) {
		super(id);
		this.category = category;
		this.title = title;
		this.description = description;
		this.owner = owner;
		this.contributors = contributors;
	}

	public Board(String objectId) {
		super(objectId);
	}

	@Override
	public Category getCategory() {
		return this.category;
	}

	public List<User> getContributors() {
		return this.contributors;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getTitle() {
		return this.title;
	}

	public String getDescription() {
		return this.description;
	}

	public User getOwner() {
		return this.owner;
	}

	public void setIsUserFollowing(boolean isFollowing) {
		this.isFollowing = isFollowing;
	}

	public boolean getIsUserFollowing() {
		return this.isFollowing;
	}

	public void setName(String string) {
		this.title = string;

	}

	public void setDescription(String string) {
		this.description = string;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public BoardAffiliation getBoardAffiliation(LocalUser user) {
		if (this.affiliation != null)
			return this.affiliation;
		if (this.owner != null && this.owner.equals(user)) {
			affiliation = BoardAffiliation.Owner;
		} else if (this.contributors != null
				&& this.contributors.contains(user)) {
			affiliation = BoardAffiliation.Contributor;
		} else if (user.getFollowedBoards().contains(this)) {
			affiliation = BoardAffiliation.Follower;
		} else {
			affiliation = BoardAffiliation.None;
		}
		return affiliation;
	}

	public enum BoardAffiliation {
		Owner, Contributor, Follower, None
	}
}
