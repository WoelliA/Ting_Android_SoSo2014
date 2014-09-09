package de.ur.mi.android.ting.utilities.view;

import de.ur.mi.android.ting.utilities.view.Notify.LoadingContext;

public interface INotify {
	public void notify(String title, String content, NotifyKind kind);


	public LoadingContext notifyLoading(int titleResourceId);
}
