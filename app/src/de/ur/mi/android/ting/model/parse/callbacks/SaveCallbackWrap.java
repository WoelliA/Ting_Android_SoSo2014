package de.ur.mi.android.ting.model.parse.callbacks;

import com.parse.ParseException;
import com.parse.SaveCallback;

import de.ur.mi.android.ting.utilities.IDoneCallback;

public class SaveCallbackWrap extends SaveCallback {

	private IDoneCallback<Void> callback;

	public SaveCallbackWrap(IDoneCallback<Void> callback) {
		this.callback = callback;
	}

	@Override
	public void done(ParseException e) {
		if (e == null) {
			callback.done(null);
		} else {
			callback.fail(e);
		}

	}

}
