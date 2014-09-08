package de.ur.mi.android.ting.model.dummy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Singleton;

import de.ur.mi.android.ting.model.CategoryProviderBase;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.Category;
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
		if (callback == null || callback.getIsDone()) {
			return;
		}

		DelayTask task = new DelayTask() {
			@Override
			protected void onPostExecute(Void result) {
				ArrayList<Category> cats = new ArrayList<Category>();
				for (int i = 0; i < 20; i++) {
					cats.add(new DummyCategory(i));
				}
				DummyCategoryProvider.this.categories = cats;
				callback.done(DummyCategoryProvider.this.categories);
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
						for (Category category : DummyCategoryProvider.this.categories) {
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
