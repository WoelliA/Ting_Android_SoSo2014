package de.ur.mi.android.ting.utilities.initialization;

import de.ur.mi.android.ting.utilities.IDoneCallback;

public interface IInitializeable {
	public void initialize(IDoneCallback<Void> callback);
}
