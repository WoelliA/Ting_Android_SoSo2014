package de.ur.mi.android.ting.app.activities;

import javax.inject.Inject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
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
import de.ur.mi.android.ting.model.primitives.Board.BoardAffiliation;
import de.ur.mi.android.ting.model.primitives.Pin;

@SuppressLint("InflateParams")
public class BoardDetailsActivity extends BaseActivity implements
		IBoardDetailsView {

	public static final String BOARD_ID_KEY = "board_id";

	@Inject
	public BoardDetailsController controller;

	private ListView listView;

	private View headerView;

	private ToggleButton followToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_boarddetails);

		Intent intent = this.getIntent();
		String boardId = intent.getExtras().getString(BOARD_ID_KEY);
		this.controller.init(this, boardId);

		BoardPinViewResolver viewResolver = new BoardPinViewResolver(this);
		PagingListAdapterBase<Pin> adapter = new PinListAdapter(this,
				this.controller, viewResolver);
		this.listView = (ListView) this.findViewById(R.id.list);
		this.listView.setAdapter(adapter);

		this.headerView = this.getLayoutInflater().inflate(
				R.layout.board_details_header_view, null, false);
		this.listView.addHeaderView(this.headerView);
	}

	@Override
	public void displayBoardInfo(final Board board) {
		TextView titleText = (TextView) this.headerView
				.findViewById(R.id.textview_board_title);
		titleText.setText(board.getTitle());

		this.followToggle = (ToggleButton) this.headerView
				.findViewById(R.id.togglebutton_follow_board);
	}

	@Override
	public void showOwnerState() {
		Button editButton = (Button) this.headerView
				.findViewById(R.id.button_edit_board);
		editButton.setVisibility(View.VISIBLE);
	}

	@Override
	public void showDefaultState(BoardAffiliation affiliation) {
		this.followToggle.setVisibility(View.VISIBLE);
		if (affiliation == BoardAffiliation.Follower) {
			this.followToggle.setChecked(true);
		}
		this.followToggle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean follow = ((CompoundButton) v).isChecked();
				BoardDetailsActivity.this.controller.setFollowBoard(follow);
			}
		});

	}

	@Override
	public Context getContext() {
		return this;
	}
}
