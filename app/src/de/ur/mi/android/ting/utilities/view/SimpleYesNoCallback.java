package de.ur.mi.android.ting.utilities.view;


public abstract class SimpleYesNoCallback implements IYesNoCallback {

	@Override
	public abstract void onYes();

	@Override
	public void onNo() {
		// just do nothing
	}

}
