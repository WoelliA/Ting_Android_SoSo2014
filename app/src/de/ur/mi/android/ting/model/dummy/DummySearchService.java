package de.ur.mi.android.ting.model.dummy;

import java.util.ArrayList;

import de.ur.mi.android.ting.model.ISearchService;
import de.ur.mi.android.ting.model.primitives.SearchRequest;
import de.ur.mi.android.ting.model.primitives.SearchResult;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public class DummySearchService implements ISearchService {


	public class Result<T> {

		private SearchRequest request;

		public Result(SearchRequest request) {
			this.request = request;
		}

		public SearchResult<T> execute() {
			ArrayList<T> list = new ArrayList<T>();
			for (int i = 0; i < this.request.getCount(); i++) {
				int num = i + this.request.getOffset();

				T item = (T) this.createItem(num);
				list.add(item);
			}

			return new SearchResult<T>(list, this.request.getCount());
		}

		private Object createItem(int num) {
			Object item = null;
			switch (this.request.getType()) {
			case PIN:
				item = new DummyPin(num);
				break;
			case BOARD:
				item = new DummyBoard(num);
				break;
			case USER:
				item = new DummyUser(num);
				break;
			}
			return item;
		}

	}

	@Override
	public <T> void search(final SearchRequest request,
			final IDoneCallback<SearchResult<T>> callback) {
		DelayTask delayTask = new DelayTask(DummyConfig.DUMMY_SIMULATED_NETWORK_DELAY_inmilliseconds) {
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				callback.done(new Result(request).execute());
			}
		};
		delayTask.execute();
		
	}

}
