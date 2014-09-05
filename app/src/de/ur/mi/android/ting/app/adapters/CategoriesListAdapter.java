package de.ur.mi.android.ting.app.adapters;

import java.util.HashMap;
import java.util.List;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.utilities.view.ViewResolver;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class CategoriesListAdapter extends
		ViewCreationDelegatingListAdapter<Category> {

	public CategoriesListAdapter(Context context, ViewResolver<Category> viewResolver) {
		super(context, viewResolver);
	}
}
