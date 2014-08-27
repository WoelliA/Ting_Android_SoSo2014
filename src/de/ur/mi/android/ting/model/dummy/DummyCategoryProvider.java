package de.ur.mi.android.ting.model.dummy;

import java.util.ArrayList;

import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.ICategoryReceivedCallback;
import de.ur.mi.android.ting.model.Primitives.Category;

public class DummyCategoryProvider implements ICategoryProvider {

	@Override
	public void GetAllCategories(ICategoryReceivedCallback callback) {
		try {
			Thread.sleep(1500);
			ArrayList<Category> categories = new ArrayList<Category>();
			for (int i = 0; i < 20; i++) {
				categories.add(new DummyCategory(i));
			}
			callback.onCategoriesReceived(categories);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
