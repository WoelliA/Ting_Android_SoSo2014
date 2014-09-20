package de.ur.mi.android.ting.app.viewResolvers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.ISelectedListener;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.utilities.IMultiSelectionListener;
import de.ur.mi.android.ting.utilities.MultiSelectionManager;
import de.ur.mi.android.ting.utilities.SelectionManager;

public class MultiSelectCategoryViewResolver extends CategoryViewResolver {
	
	private SelectionManager<Category> selectionManager;

	public MultiSelectCategoryViewResolver(int resourceId, Context context, IMultiSelectionListener<Category> selectionListener) {
		super(resourceId, context, null);	
		this.selectionManager = new MultiSelectionManager<Category>(selectionListener);
	}

	@Override
	protected void decorateView(View view, final Category category,
			ViewGroup parent) {
		super.decorateView(view, category, parent);
		this.findViewById(view, R.id.category_favorite_button).setVisibility(
				View.GONE);

		View selectable = this.findViewById(view, R.id.selectable);
		selectionManager.setSelectedState(selectable, category);
		
		selectable.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectionManager.toggleSelected(v, category);
			}
		});
	}

	@Override
	public boolean skipInject() {
		return true;
	}
}
