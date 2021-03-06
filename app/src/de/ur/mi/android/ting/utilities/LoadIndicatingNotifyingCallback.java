package de.ur.mi.android.ting.utilities;

import de.ur.mi.android.ting.utilities.view.Notify;
import de.ur.mi.android.ting.utilities.view.Notify.LoadingContext;

public class LoadIndicatingNotifyingCallback<T> extends NotifyingCallback<T>{

	private LoadingContext loadingContext;

	public LoadIndicatingNotifyingCallback(int successResourceId) {
		super(successResourceId);		
		this.initLoading();
	}
	

	public LoadIndicatingNotifyingCallback(String successMessage) {
		super(successMessage);
		this.initLoading();
	}
	private void initLoading() {
		this.loadingContext = Notify.current().showLoading(-1);		
	}
	
	@Override
	public void done(T result) {
		this.loadingContext.close();
		super.done(result);
	}

	@Override
	public void fail(Exception e) {
		this.loadingContext.close();
		super.fail(e);
	}
}
