package de.ur.mi.android.ting.app.controllers;

import java.util.ArrayList;

import javax.inject.Inject;

import de.ur.mi.android.ting.app.IInjector;
import de.ur.mi.android.ting.model.IPinService;
import de.ur.mi.android.ting.model.IPinReceivedCallback;
import de.ur.mi.android.ting.model.PagingResult;
import de.ur.mi.android.ting.model.PinRequest;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.utilities.IConnectivity;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public class PinListController extends PagingController<Pin> {

	private Category category;

	@Inject
	public IPinService pinService;

	private int requestCount = 10;

	@Inject
	public IConnectivity connectivity;

	public PinListController(Category category, IInjector injector) {
		this.category = category;
		injector.inject(this);
	}

	@Override
	protected void loadNextPage(int offset,
			final IDoneCallback<PagingResult<Pin>> doneCallback) {
		if(!this.connectivity.hasWebAccess(true)){
			return;
		}				
		
		PinRequest request = new PinRequest(offset, this.requestCount);
		this.pinService.getPinsForCategory(this.category, request,
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
