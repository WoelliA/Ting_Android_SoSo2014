package de.ur.mi.android.ting.model.primitives;

public class User extends UniqueBase {

	private String name;
	private String profilePictureUri;

	public User(String id, String name, String profilePictureUri) {
		super(id);
		this.name = name;
		this.profilePictureUri = profilePictureUri;

	}

	public User(String id) {
		super(id);
	}

	public String getName() {
		return this.name;
	}

	public String getProfilePictureUri() {
		return this.profilePictureUri;
	}

	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUri = profilePictureUrl;

	}

	public void setName(String name) {
		this.name = name;
	}
}
