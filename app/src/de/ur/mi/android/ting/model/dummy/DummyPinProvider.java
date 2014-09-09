package de.ur.mi.android.ting.model.dummy;

import java.util.ArrayList;

import android.os.AsyncTask;
import de.ur.mi.android.ting.model.IPinProvider;
import de.ur.mi.android.ting.model.IPinReceivedCallback;
import de.ur.mi.android.ting.model.PinData;
import de.ur.mi.android.ting.model.PinRequest;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public class DummyPinProvider implements IPinProvider {

	@Override
	public void getPinsForCategory(Category category, final PinRequest request,
			final IPinReceivedCallback callback) {
		AsyncTask<Void,Void, ArrayList<Pin>> pinTask = new AsyncTask<Void, Void, ArrayList<Pin>>() {
				
			@Override
			protected ArrayList<Pin> doInBackground(Void... params) {
				try {
					Thread.sleep(DummyConfig.DUMMY_SIMULATED_NETWORK_DELAY_inmilliseconds);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return DummyPinProvider.this.createCategories(request);
			}

			@Override
			protected void onPostExecute(ArrayList<Pin> result) {
				callback.onPinsReceived(result);
				super.onPostExecute(result);
			}
			
		};
		pinTask.execute();
		
	}
	
	private ArrayList<Pin> createCategories(PinRequest request) {
		ArrayList<Pin> articles = new ArrayList<Pin>();
		for (int i = 0; i < request.getCount(); i++) {
			articles.add(new DummyPin(request.getOffset() + i));
		}
		return articles;
	}

	@Override
	public void createPin(PinData result, Board selectedBoard, final IDoneCallback<Void> callback) {
		DelayTask task = new DelayTask() {
			@Override
			protected void onPostExecute(Void result) {
				callback.done(null);
				super.onPostExecute(result);
			}
		};
		task.execute();
	}

}
