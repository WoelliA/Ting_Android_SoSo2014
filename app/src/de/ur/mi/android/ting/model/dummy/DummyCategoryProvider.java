package de.ur.mi.android.ting.model.dummy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.inject.Singleton;

import de.ur.mi.android.ting.model.CategoryProviderBase;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.ISpecialCategories;
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
	public void getFavoriteCategories(LocalUser user,
			final IDoneCallback<List<Category>> callback) {
		this.getAllCategories(new SimpleDoneCallback<Collection<Category>>() {

			@Override
			public void done(Collection<Category> result) {
				DelayTask task = new DelayTask() {
					@Override
					protected void onPostExecute(Void result) {
						super.onPostExecute(result);
						Random random = new Random();
						List<Category> favorites = new ArrayList<Category>();
						for (Category category : DummyCategoryProvider.super.getCategories()) {
							int r = random.nextInt();
							if (r % 4 == 0) {
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

	@Override
	protected void getAllCategoriesImpl(
			final IDoneCallback<List<Category>> callback) {
		DelayTask task = new DelayTask() {
			@Override
			protected void onPostExecute(Void result) {
				List<Category> categories = new ArrayList<Category>();
				for (int i = 0; i < 20; i++) {
					categories.add(new DummyCategory(i));
				}
				callback.done(categories);
			}
		};
		task.execute();
		
	}
}
