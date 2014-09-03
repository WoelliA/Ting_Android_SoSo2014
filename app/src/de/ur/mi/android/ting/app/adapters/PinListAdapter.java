package de.ur.mi.android.ting.app.adapters;

import android.content.Context;
import de.ur.mi.android.ting.app.viewResolvers.PinListViewResolver;
import de.ur.mi.android.ting.model.IPaging;
import de.ur.mi.android.ting.model.primitives.Pin;

public class PinListAdapter extends
		ViewCreationDelegatingPagingListAdapter<Pin> {

	public PinListAdapter(Context context, IPaging<Pin> paging) {
		super(context, paging, new PinListViewResolver(context));
	}

}
