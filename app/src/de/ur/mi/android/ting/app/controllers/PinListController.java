package de.ur.mi.android.ting.app.controllers;

import java.util.ArrayList;

import de.ur.mi.android.ting.model.IPinProvider;
import de.ur.mi.android.ting.model.IPinReceivedCallback;
import de.ur.mi.android.ting.model.PagingResult;
import de.ur.mi.android.ting.model.PinRequest;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public class PinListController extends PagingController<Pin> {

	private Category category;

	public IPinProvider pinProvider;

	private int requestCount = 10;

	public PinListController(Category category, IPinProvider pinProvider) {
		this.category = category;
		this.pinProvider = pinProvider;
	}

	@Override
	protected void loadNextPage(int offset,
			final IDoneCallback<PagingResult<Pin>> doneCallback) {

		PinRequest request = new PinRequest(offset, this.requestCount);
		this.pinProvider.getPinsForCategory(this.category, request,
				new IPinReceivedCallback() {
					@Override
					public void onPinsReceived(ArrayList<Pin> pins) {
						if (doneCallback != null) {
							doneCallback.done(new PagingResult<Pin>(
									PinListController.this.requestCount, pins));
						}
					}
				});
	}

}
