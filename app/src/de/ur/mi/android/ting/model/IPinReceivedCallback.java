package de.ur.mi.android.ting.model;

import java.util.ArrayList;
import java.util.Collection;

import de.ur.mi.android.ting.model.primitives.Pin;

public interface IPinReceivedCallback {
	public void onPinsReceived(Collection<Pin> pins);
}
