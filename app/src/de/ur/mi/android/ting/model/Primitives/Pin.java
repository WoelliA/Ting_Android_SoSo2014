package de.ur.mi.android.ting.model.primitives;

public class Pin extends UniqueBase implements IHasCategory{

	private String title;
	private String description;
	private String imageUri;
	private Board board;
	private String linkUri;
	private double aspectratio;
	

	public Pin(String id, String title, String description, String imageUri, Board board, String linkUri, double aspectratio) {
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
		return board.getCategory();
	}

	public double getAspectRatio(){
		return this.aspectratio;
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
