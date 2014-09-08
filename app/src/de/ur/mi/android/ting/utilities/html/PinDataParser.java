package de.ur.mi.android.ting.utilities.html;

import java.net.URL;

import de.ur.mi.android.ting.utilities.IDoneCallback;

public interface PinDataParser {
	public void getPinData(URL uri, IDoneCallback<PinData> callback);
}
