package de.ur.mi.android.ting.app.viewResolvers;

import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.utilities.view.ViewResolver;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BoardViewResolver extends ViewResolver<Board> {

	public BoardViewResolver(Context activity) {
		super(android.R.layout.simple_list_item_2, activity);
	}

	@Override
	protected void decorateView(View view, Board dataItem, ViewGroup parent) {
		TextView headerText = (TextView) this.findViewById(view,
				android.R.id.text1);
		TextView descriptionTest = (TextView) this.findViewById(view,
				android.R.id.text2);

		headerText.setText(dataItem.getTitle());
		descriptionTest.setText(dataItem.getDescription());
	}
}
