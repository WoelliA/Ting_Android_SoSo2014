package de.ur.mi.android.ting.model.dummy;

import android.os.AsyncTask;

public abstract class DelayTask extends AsyncTask<Void, Void, Void>{

	@Override
	protected Void doInBackground(Void... params) {
		try {
			Thread.sleep(DummyConfig.DUMMY_SIMULATED_NETWORK_DELAY_inmilliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

}
