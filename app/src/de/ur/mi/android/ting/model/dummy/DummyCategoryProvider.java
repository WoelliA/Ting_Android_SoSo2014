package de.ur.mi.android.ting.model.dummy;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import android.os.AsyncTask;
import de.ur.mi.android.ting.model.CategoryProviderBase;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.ICategoryReceivedCallback;
import de.ur.mi.android.ting.model.IStringArrayCallback;
import de.ur.mi.android.ting.model.primitives.Category;

public class DummyCategoryProvider extends CategoryProviderBase implements
		ICategoryProvider {

	@Override
	public void getAllCategories(final ICategoryReceivedCallback callback) {
		AsyncTask<Void, Void, ArrayList<Category>> task = new AsyncTask<Void, Void, ArrayList<Category>>() {
			
			@Override
			protected ArrayList<Category> doInBackground(Void... params) {
				try {
					Thread.sleep(DummyConfig.DUMMY_SIMULATED_NETWORK_DELAY_inmilliseconds);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ArrayList<Category> categories = new ArrayList<Category>();
				for (int i = 0; i < 20; i++) {
					categories.add(new DummyCategory(i));
				}
				return categories;
			}
			
			@Override
			protected void onPostExecute(ArrayList<Category> result) {
				callback.onCategoriesReceived(result);
				super.onPostExecute(result);
			}
		};
		task.execute();
	}

	@Override
	public Category resolveCategoryByName(String name) {
		return new DummyCategory(10);
	}

	@Override
	public void getAllCategoryNames(final IStringArrayCallback callback) {
		this.getAllCategories(new ICategoryReceivedCallback() {

			@Override
			public void onCategoriesReceived(List<Category> categories) {
				String[] categorynames = getCategoryNames(categories);
				callback.onStringArrayReceived(categorynames);
			}

		});
	}

}
