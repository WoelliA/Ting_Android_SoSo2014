package de.ur.mi.android.ting.model.primitives;

public class Category extends UniqueBase {
	public Category(String id, String name, String shortName) {
		super(id);
		this.setName(name);
		this.setShortName(shortName);
	}

	public Category(String id) {
		super(id);
	}

	private String name;
	
	private String shortName;

	private boolean isFavorite;
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public void setIsFavorite(boolean isFavorite){
		this.isFavorite = isFavorite;
	}
	
	public boolean getIsFavorite() {
		return this.isFavorite;
	}
	
	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}
}
