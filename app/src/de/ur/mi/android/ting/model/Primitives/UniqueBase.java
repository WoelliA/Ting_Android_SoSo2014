package de.ur.mi.android.ting.model.primitives;

public abstract class UniqueBase implements IUnique {
	private String id;

	@Override
	public String getId() {
		return this.id;
	}

	private void setId(String id) {
		this.id = id;
	}
	
	public UniqueBase(String id){
		this.setId(id);
	}
}
