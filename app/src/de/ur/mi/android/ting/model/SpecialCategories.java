package de.ur.mi.android.ting.model;

import android.content.Context;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.model.primitives.Category;

public class SpecialCategories implements ISpecialCategories {
	private Context context;

	public SpecialCategories(Context context) {
		this.context = context;
	}

	public static class SpecialCategory extends Category {

		private int specialId;

		public SpecialCategory(int id, String name) {
			super("", name, "");
			this.specialId = id;
		}

		public int getSpecialId() {
			return this.specialId;
		}
	}

	@Override
	public Category getFeedCategory() {
		return new SpecialCategory(R.string.special_category_feed_name,
				this.context.getString(R.string.special_category_feed_name));
	}

	@Override
	public Category getEverythingCategory() {
		return new SpecialCategory(R.string.special_category_everything_name,
				this.context.getString(R.string.special_category_everything_name));
	}

}
