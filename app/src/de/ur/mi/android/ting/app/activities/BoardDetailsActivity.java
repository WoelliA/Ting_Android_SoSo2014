package de.ur.mi.android.ting.app.activities;

import javax.inject.Inject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.adapters.PagingListAdapterBase;
import de.ur.mi.android.ting.app.adapters.PinListAdapter;
import de.ur.mi.android.ting.app.controllers.BoardDetailsController;
import de.ur.mi.android.ting.app.controllers.BoardDetailsController.IBoardDetailsView;
import de.ur.mi.android.ting.app.viewResolvers.BoardPinViewResolver;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Pin;

@SuppressLint("InflateParams")
public class BoardDetailsActivity extends BaseActivity implements
		IBoardDetailsView {

	public static final String BOARD_ID_KEY = "board_id";
	
	
	@Inject
	public BoardDetailsController controller;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_boarddetails);

		Intent intent = getIntent();
		String boardId = intent.getExtras().getString(BOARD_ID_KEY);

		BoardPinViewResolver viewResolver = new BoardPinViewResolver(this);
		PagingListAdapterBase<Pin> adapter = new PinListAdapter(this,
				controller, viewResolver);
		this.listView = (ListView) this.findViewById(R.id.list);
		listView.setAdapter(adapter);
		controller.init(this, boardId);
	}

	@Override
	public void displayBoardInfo(Board board) {
		View headerView = this.createHeaderView(board);
		this.listView.addHeaderView(headerView);
	}

	private View createHeaderView(Board board) {
		View view = getLayoutInflater().inflate(R.layout.board_header_view,
				null, false);
		TextView titleText = (TextView) view.findViewById(R.id.board_title);
		titleText.setText(board.getTitle());

		ToggleButton followToggle = (ToggleButton) view
				.findViewById(R.id.board_follow_toggle);
		followToggle.setChecked(board.getIsUserFollowing());
		
		return view;
	}

}
