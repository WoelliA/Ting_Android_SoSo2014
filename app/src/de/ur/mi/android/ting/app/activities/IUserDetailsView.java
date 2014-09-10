package de.ur.mi.android.ting.app.activities;

import de.ur.mi.android.ting.app.fragments.ICollectionView;
import de.ur.mi.android.ting.model.primitives.User;

public interface IUserDetailsView extends ICollectionView {
	public void setProfileInfo(User user);
}