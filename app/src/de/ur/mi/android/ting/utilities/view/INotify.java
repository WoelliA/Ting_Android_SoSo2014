package de.ur.mi.android.ting.utilities.view;

import de.ur.mi.android.ting.utilities.view.Notify.LoadingContext;

public interface INotify {

	public LoadingContext showLoading(int titleResourceId);


	void showDialog(int titleResourceId, int contentResourceId, NotifyKind kind);
}
