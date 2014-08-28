package de.ur.mi.android.ting.model.dummy;

import java.util.ArrayList;
import java.util.List;

import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.ICategoryReceivedCallback;
import de.ur.mi.android.ting.model.IStringArrayCallback;
import de.ur.mi.android.ting.model.Primitives.Category;

public class DummyCategoryProvider implements ICategoryProvider {

	@Override
	public void getAllCategories(ICategoryReceivedCallback callback) {
	
			ArrayList<Category> categories = new ArrayList<Category>();
			for (int i = 0; i < 20; i++) {
				categories.add(new DummyCategory(i));
			}
			callback.onCategoriesReceived(categories);
		

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
				String[] categorynames = new String[categories.size()];
				for (int i = 0; i < categories.size(); i++) {
					Category cate = categories.get(i);

					categorynames[i]=(cate.getName());
				}				
				callback.onStringArrayReceived( categorynames);
			}
		});
	}
	
	

}
