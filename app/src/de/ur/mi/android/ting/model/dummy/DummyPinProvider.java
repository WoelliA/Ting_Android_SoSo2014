package de.ur.mi.android.ting.model.dummy;

import java.util.ArrayList;
import java.util.List;

import de.ur.mi.android.ting.model.IPinService;
import de.ur.mi.android.ting.model.IPinReceivedCallback;
import de.ur.mi.android.ting.model.PinData;
import de.ur.mi.android.ting.model.PinRequest;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public class DummyPinProvider implements IPinService {

	@Override
	public void getPinsForCategory(Category category, final PinRequest request,
			final IPinReceivedCallback callback) {
		DelayTask pinTask = new DelayTask(){
				
			@Override
			protected void onPostExecute(Void result) {
				callback.onPinsReceived(DummyPinProvider.this.createDummyPins(request));
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

	@Override
	public void getPinsForBoard(String boardId, final PinRequest request,
			final IDoneCallback<List<Pin>> callback) {
		DelayTask task = new DelayTask() {
			@Override
			protected void onPostExecute(Void result) {
				callback.done(createDummyPins(request));
				super.onPostExecute(result);
			}
		};
		task.execute();
		
	}

}
