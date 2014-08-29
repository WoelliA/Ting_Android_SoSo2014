package de.ur.mi.android.ting.model.dummy;

import java.util.ArrayList;

import de.ur.mi.android.ting.model.IPinProvider;
import de.ur.mi.android.ting.model.IPinReceivedCallback;
import de.ur.mi.android.ting.model.PinRequest;
import de.ur.mi.android.ting.model.Primitives.Category;
import de.ur.mi.android.ting.model.Primitives.Pin;

public class DummyPinProvider implements IPinProvider {

	@Override
	public void getPinsForCategory(Category category, PinRequest request,
			IPinReceivedCallback callback) {
		ArrayList<Pin> articles = new ArrayList<Pin>();
		for (int i = 0; i < request.getCount(); i++) {
			articles.add(new DummyPin(request.getOffset() + i));
		}
		callback.onPinsReceived(articles);
	}

}
