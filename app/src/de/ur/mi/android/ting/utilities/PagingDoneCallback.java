package de.ur.mi.android.ting.utilities;

import java.util.Collection;

import de.ur.mi.android.ting.model.PagingResult;
import de.ur.mi.android.ting.model.primitives.PagingRequestBase;

public class PagingDoneCallback<T> extends SimpleDoneCallback<Collection<T>> {

	private PagingRequestBase request;
	private IDoneCallback<PagingResult<T>> callback;

	public PagingDoneCallback(PagingRequestBase request,
			IDoneCallback<PagingResult<T>> callback) {
		this.request = request;
		this.callback = callback;
	}

	@Override
	public void done(Collection<T> result) {
		callback.done(new PagingResult<T>(request.getCount(), result));
	}

}
