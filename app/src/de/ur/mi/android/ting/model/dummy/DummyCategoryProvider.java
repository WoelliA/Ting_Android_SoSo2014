package de.ur.mi.android.ting.model.dummy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Singleton;

import android.os.AsyncTask;
import de.ur.mi.android.ting.model.CategoryProviderBase;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.ICategoryReceivedCallback;
import de.ur.mi.android.ting.model.IStringArrayCallback;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

@Singleton
public class DummyCategoryProvider extends CategoryProviderBase implements
		ICategoryProvider {

	public DummyCategoryProvider(LocalUser user) {
		super(user);
	}

	@Override
	public void getAllCategories(final IDoneCallback<List<Category>> callback) {
		super.getAllCategories(callback);
		if (callback == null || callback.getIsDone())
			return;

		AsyncTask<Void, Void, List<Category>> task = new AsyncTask<Void, Void, List<Category>>() {

			@Override
			protected List<Category> doInBackground(Void... params) {
				try {
					Thread.sleep(DummyConfig.DUMMY_SIMULATED_NETWORK_DELAY_inmilliseconds);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < 20; i++) {
					categories.add(new DummyCategory(i));
				}
				return categories;
			}

			@Override
			protected void onPostExecute(List<Category> result) {
				callback.done(result);
				super.onPostExecute(result);
			}
		};
		task.execute();
	}

	@Override
	public void addFavoriteCategories(LocalUser user,
			final IDoneCallback<List<Category>> callback) {
		this.getAllCategories(new SimpleDoneCallback<List<Category>>() {

			@Override
			public void done(List<Category> result) {
				DelayTask task = new DelayTask() {
					@Override
					protected void onPostExecute(Void result) {
						super.onPostExecute(result);
						Random random = new Random();
						List<Category> favorites = new ArrayList<Category>();
						for (Category category : categories) {
							int r = random.nextInt();
							if (r % 4 == 0) {
								category.setIsFavorite(true);
								favorites.add(category);
							}
						}
						callback.done(favorites);
					}
				};
				task.execute();
			}
		});

	}

	@Override
	public void saveIsFavoriteCategory(Category category, boolean isChecked) {

	}
}
