package de.ur.mi.android.ting.app.adapters;

import android.content.Context;
import de.ur.mi.android.ting.app.viewResolvers.PinViewResolver;
import de.ur.mi.android.ting.model.IPaging;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.utilities.view.ViewResolver;

public class PinListAdapter extends
		ViewCreationDelegatingPagingListAdapter<Pin> {

	public PinListAdapter(Context context, IPaging<Pin> paging) {
		super(context, new PinViewResolver(context), paging);
	}

	public PinListAdapter(Context context, IPaging<Pin> paging,
			ViewResolver<Pin> viewResolver) {
		super(context, viewResolver, paging);
	}

}
