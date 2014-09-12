package de.ur.mi.android.ting.utilities.view;

import de.ur.mi.android.ting.utilities.view.Notify.LoadingContext;

public interface INotify {

	public LoadingContext showLoading(int titleResourceId);
	void show(int titleResourceId, int contentResourceId, NotifyKind kind);
	void showToast(String content);
	void showToast(int contentResourceId);
}
