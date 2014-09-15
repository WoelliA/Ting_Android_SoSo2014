package de.ur.mi.android.ting.app.fragments;

import de.ur.mi.android.ting.model.primitives.UniqueBase;

public class Gender extends UniqueBase {

	private String name;

	public Gender(String id, String name) {
		super(id);
		this.name = name;
		
	}

	public CharSequence getName() {
		return this.name;
	}
	
}
