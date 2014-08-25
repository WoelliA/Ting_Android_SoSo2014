package de.ur.mi.android.ting.model.Primitives;

import java.util.List;

public class User extends UniqueBase {

	private String name;
	
	public User(String id, String name) {
		super(id);
		this.name = name;
		
	}
	
	public String getName(){
		return this.name;
	}
}