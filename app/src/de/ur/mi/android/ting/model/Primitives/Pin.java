package de.ur.mi.android.ting.model.primitives;

import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.PinAffiliation;

public class Pin extends UniqueBase implements IHasCategory {

	private String title;
	private String description;
	private String imageUri;
	private Board board;
	private String linkUri;
	private double aspectratio;
	private PinAffiliation affiliation;

	public Pin(String id, String title, String description, String imageUri,
			Board board, String linkUri, double aspectratio) {
		super(id);

		this.title = title;
		this.description = description;
		this.imageUri = imageUri;
		this.board = board;
		this.linkUri = linkUri;
		this.aspectratio = aspectratio;
	}

	@Override
	public Category getCategory() {
		return this.board.getCategory();
	}

	public double getAspectRatio() {
		return this.aspectratio;
	}

	public User getAuthor() {
		return this.board.getOwner();
	}

	public String getTitle() {
		return this.title;
	}

	public String getImageUri() {
		return this.imageUri;
	}

	public String getDescription() {
		return this.description;
	}

	public String getLinkUri() {
		return this.linkUri;
	}

	public PinAffiliation getAffiliation(LocalUser user) {
		if (affiliation != null)
			return affiliation;

		if (user.getLikedPins().contains(this)) {
			affiliation = PinAffiliation.Liked;
		} else if (this.board != null && user.getOwnedBoards().contains(this.board)){
			affiliation = PinAffiliation.Owned;
		} else {
			affiliation = PinAffiliation.None;
		}

		return this.affiliation;
	}

	public void setAffiliation(PinAffiliation pinAffiliation) {
		affiliation = pinAffiliation;		
	}

}
