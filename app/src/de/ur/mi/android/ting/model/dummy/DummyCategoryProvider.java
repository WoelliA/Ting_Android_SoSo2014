package de.ur.mi.android.ting.model.dummy;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;

import de.ur.mi.android.ting.model.CategoryProviderBase;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.utilities.IDoneCallback;

@Singleton
public class DummyCategoryProvider extends CategoryProviderBase implements
		ICategoryProvider {

	public DummyCategoryProvider(LocalUser user, IUserService userService) {
		super(user, userService);
	}

	@Override
	protected void getAllCategoriesImpl(
			final IDoneCallback<List<Category>> callback) {
		List<Category> categories = new ArrayList<Category>();
		for (int i = 0; i < 20; i++) {
			categories.add(new DummyCategory(i));
		}
		DummyResultTask<List<Category>> task = new DummyResultTask<List<Category>>(
				categories, callback);
	}
}
