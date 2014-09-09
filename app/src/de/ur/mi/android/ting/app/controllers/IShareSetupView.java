package de.ur.mi.android.ting.app.controllers;

import de.ur.mi.android.ting.model.PinData;

public interface IShareSetupView {

	int NO_TEXT = 1;
	int NO_URL = 0;
	int LOAD_ERROR = 2;

	void addDisplayPinnableImage(PinData result);

}
