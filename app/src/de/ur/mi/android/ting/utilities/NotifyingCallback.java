package de.ur.mi.android.ting.utilities;

import de.ur.mi.android.ting.utilities.view.Notify;

public class NotifyingCallback<T> extends SimpleDoneCallback<T> {

	private String successMessage;
	private int successResourceId;

	public NotifyingCallback(String successMessage) {
		this.successMessage = successMessage;
	}

	public NotifyingCallback(int successResourceId) {
		this.successResourceId = successResourceId;
	}

	@Override
	public void done(T result) {
		if (this.successMessage == null || this.successMessage.trim().length() == 0) {
			if(this.successResourceId <= 0){
				return;
			}
			Notify.current().showToast(this.successResourceId);
		} else {
			Notify.current().showToast(this.successMessage);
		}
	}

	@Override
	protected boolean handleException(Exception e) {
		Notify.current().showToast(e.getLocalizedMessage());
		return true;
	}

}
