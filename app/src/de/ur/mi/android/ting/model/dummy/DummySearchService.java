package de.ur.mi.android.ting.model.dummy;

import java.util.ArrayList;
import android.os.AsyncTask;
import de.ur.mi.android.ting.model.ISearchService;
import de.ur.mi.android.ting.model.primitives.SearchRequest;
import de.ur.mi.android.ting.model.primitives.SearchResult;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public class DummySearchService implements ISearchService {

	@Override
	public <T> void search(final SearchRequest request,
			final IDoneCallback<SearchResult<T>> callback) {
		AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

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
				callback.done(new Result<T>(request).execute());
			}
		};
		task.execute();

	}

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

}
