package de.ur.mi.android.ting.model.dummy;

import java.util.ArrayList;
import java.util.Collection;
import de.ur.mi.android.ting.model.IPinService;
import de.ur.mi.android.ting.model.IPinReceivedCallback;
import de.ur.mi.android.ting.model.PinData;
import de.ur.mi.android.ting.model.PinRequest;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.model.primitives.SearchRequest;
import de.ur.mi.android.ting.model.primitives.SearchResult;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public class DummyPinProvider implements IPinService {

	@Override
	public void getPinsForCategory(Category category, final PinRequest request,
			final IPinReceivedCallback callback) {
		
		
		DelayTask pinTask = new DelayTask(DummyConfig.DUMMY_SIMULATED_NETWORK_DELAY_inmilliseconds) {

			@Override
			protected void onPostExecute(Void result) {
				callback.onPinsReceived(DummyPinProvider.this
						.createDummyPins(request));
				super.onPostExecute(result);
			}

		};
		pinTask.execute();

	}

	private ArrayList<Pin> createDummyPins(PinRequest request) {
		ArrayList<Pin> articles = new ArrayList<Pin>();
		for (int i = 0; i < request.getCount(); i++) {
			articles.add(new DummyPin(request.getOffset() + i));
		}
		return articles;
	}


	@Override
	public <T> void search(SearchRequest request,
			IDoneCallback<SearchResult<T>> callback) {
		// no need to do anything - generic search is implemented
	}

	@Override
	public void getPinsForBoard(String boardId, final PinRequest request,
			final IDoneCallback<Collection<Pin>> callback) {
		DelayTask task = new DelayTask(DummyConfig.DUMMY_SIMULATED_NETWORK_DELAY_inmilliseconds) {
			@Override
			protected void onPostExecute(Void result) {
				callback.done(DummyPinProvider.this.createDummyPins(request));
				super.onPostExecute(result);
			}
		};
		task.execute();

	}

	@Override
	public void getPin(String pinId, IDoneCallback<Pin> callback) {
		Pin result = new DummyPin(Integer.parseInt(pinId));
		DummyResultTask<Pin> task = new DummyResultTask<Pin>(result, callback);		
	}

	@Override
	public void createPin(PinData pin, String sharedPinId, Board selectedBoard,
			IDoneCallback<Void> callback) {
		DummyResultTask<Void> task = new DummyResultTask<Void>(null, callback);		;
		
	}
}
