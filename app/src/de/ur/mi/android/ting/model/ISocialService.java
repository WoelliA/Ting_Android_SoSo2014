package de.ur.mi.android.ting.model;

import java.util.Collection;

import de.ur.mi.android.ting.model.primitives.User;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public interface ISocialService {	
	public void getThirdPartyFriends(IDoneCallback<Collection<User>> callback);
}
