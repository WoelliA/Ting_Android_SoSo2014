package de.ur.mi.android.ting.app.controllers;

public class BoardEditRequest  {

	private String title;
	private String description;
	private String id;
	private String categoryId;

	public BoardEditRequest(String id,String title, String description, String categoryId) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.categoryId = categoryId;
	}

	public Object getTitle() {
		return title;
	}

	public Object getDescription() {
		return description;
	}
	
	public String getId(){
		return id;
	}
	
	public String getCategoryId(){
		return categoryId;
	}

}
