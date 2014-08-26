package de.ur.mi.android.ting.model.Primitives;

public class Pin extends UniqueBase implements IHasCategory{

	private String title;
	private String description;
	private String imageUri;
	private Board board;
	private String linkUri;
	

	public Pin(String id, String title, String description, String imageUri, Board board, String linkUri) {
		super(id);
		
		this.title = title;
		this.description = description;
		this.imageUri = imageUri;
		this.board = board;
		this.linkUri = linkUri;
	}


	@Override
	public Category getCategory() {
		return board.getCategory();
	}

	public User getAuthor(){
		return this.board.getOwner();
	}

	public String getTitle() {
		return title;
	}


	public String getImageUri() {
		return imageUri;
	}


	public String getDescription() {
		return description;
	}


	public String getLinkUri() {
		return linkUri;
	}

}
