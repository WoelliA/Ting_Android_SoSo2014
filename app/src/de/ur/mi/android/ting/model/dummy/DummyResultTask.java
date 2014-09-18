package de.ur.mi.android.ting.model.dummy;

import de.ur.mi.android.ting.utilities.IDoneCallback;

public class DummyResultTask<T> extends DelayTask {

	private T result;
	private IDoneCallback<T> callback;

	public DummyResultTask(T result, IDoneCallback<T> callback)
	{
		super(DummyConfig.DUMMY_SIMULATED_NETWORK_DELAY_inmilliseconds);
		this.result = result;
		this.callback = callback;
		this.execute();
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		this.callback.done(this.result);
	}
}
