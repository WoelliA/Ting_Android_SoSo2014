package de.ur.mi.android.ting.app;

import android.app.Activity;
import android.content.Context;

public class TutorialStep {

	private Class<? extends Activity> activityClass;
	private int dialogContentResId;
	private boolean shown;

	public TutorialStep(Class<? extends Activity> activityClass,
			int dialogContentResId) {
		this.activityClass = activityClass;
		this.dialogContentResId = dialogContentResId;
	}

	public String getDialogContent(Context context) {
		return context.getString(this.dialogContentResId);
	}

	public Class<?> getTargetClass() {
		return this.activityClass;
	}

	public int getDialogContentId() {
		return this.dialogContentResId;
	}

	public boolean getWasDialogShown() {
		return this.shown;
	}

	public void setWasDialogShown(boolean shown) {
		this.shown = shown;
		
	}

}
