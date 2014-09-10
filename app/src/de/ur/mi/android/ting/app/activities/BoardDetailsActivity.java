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

	private View headerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_boarddetails);

		Intent intent = this.getIntent();
		String boardId = intent.getExtras().getString(BOARD_ID_KEY);

		BoardPinViewResolver viewResolver = new BoardPinViewResolver(this);
		PagingListAdapterBase<Pin> adapter = new PinListAdapter(this,
				this.controller, viewResolver);
		this.listView = (ListView) this.findViewById(R.id.list);
		this.listView.setAdapter(adapter);
		this.controller.init(this, boardId);

		this.headerView = this.getLayoutInflater().inflate(
				R.layout.board_details_header_view, null, false);
		this.listView.addHeaderView(this.headerView);
	}

	@Override
	public void displayBoardInfo(Board board) {
		TextView titleText = (TextView) this.headerView
				.findViewById(R.id.board_title);
		titleText.setText(board.getTitle());

		ToggleButton followToggle = (ToggleButton) this.headerView
				.findViewById(R.id.board_follow_toggle);
		followToggle.setChecked(board.getIsUserFollowing());
	}
}
