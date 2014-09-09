package de.ur.mi.android.ting.utilities.view;

import de.ur.mi.android.ting.R;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public final class Loading {

	public static View getView(Context context, String title){
		View loadingView = View.inflate(context, R.layout.loading_view_layout, null);
		TextView text = (TextView)loadingView.findViewById(R.id.loading_view_text);
		text.setText(title);
		return loadingView;
	}
	
}
