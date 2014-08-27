package de.ur.mi.android.ting.model.dummy;

import de.ur.mi.android.ting.model.Primitives.User;

public class DummyUser extends User {

	public DummyUser(int id) {
		super("" +id, "User name " + id);
	}

}
