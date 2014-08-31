package de.ur.mi.android.ting.model.dummy;

import android.os.AsyncTask;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public class DummyUserService implements IUserService {

	boolean isRightLogin ;
	@Override
	public void login(String userName, String password,
			final IDoneCallback<LoginResult> callback) {
		isRightLogin= userName == "right@right.de" && password == "right";
		AsyncTask<Void, Void, Void> loginTask = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(DummyConfig.DUMMY_SIMULATED_NETWORK_DELAY_inmilliseconds);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				callback.done(new LoginResult(isRightLogin));
			}
		};
		loginTask.execute();
	}
}
