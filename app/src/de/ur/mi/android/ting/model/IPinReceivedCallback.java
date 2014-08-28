package de.ur.mi.android.ting.model;

import java.util.ArrayList;

import de.ur.mi.android.ting.model.Primitives.Pin;

public interface IPinReceivedCallback {
	public void onPinsReceived(ArrayList<Pin> pins);
}
