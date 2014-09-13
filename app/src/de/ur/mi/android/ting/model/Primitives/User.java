package de.ur.mi.android.ting.model.primitives;

public class User extends UniqueBase {

	protected String name;
	private String profilePictureUri;
	private String info;

	public User(String id, String name, String info, String profilePictureUri) {
		super(id);
		this.name = name;
		this.info = info;
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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
