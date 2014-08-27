package de.ur.mi.android.ting.model.Primitives;


public class Board extends UniqueBase implements IHasCategory{
	private String title;
	private String description;

	private Category category;
	private User owner;

	public Board(String id, Category category, String title, String description, User owner) {
		super(id);
		this.category = category;
		this.title = title;
		this.description = description;
		this.owner =owner;
	}

	public Category getCategory() {
		return category;
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
		return owner;
	}

}
