package de.ur.mi.android.ting.app.controllers;

import de.ur.mi.android.ting.model.PinData;
import de.ur.mi.android.ting.utilities.LoadedImageData;

public interface IShareSetupView {

	int NO_TEXT = 1;
	int NO_URL = 0;
	int LOAD_ERROR = 2;

	void addDisplayPinnableImage(PinData result);

	void displayError(int errorCode);

}
