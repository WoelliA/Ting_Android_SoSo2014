package de.ur.mi.android.ting.app.viewResolvers;

import java.util.List;

import javax.inject.Inject;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.controllers.BoardController;
import de.ur.mi.android.ting.app.controllers.BoardController.IBoardView;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.User;
import de.ur.mi.android.ting.model.primitives.Board.BoardAffiliation;
import de.ur.mi.android.ting.utilities.IImageLoader;
import de.ur.mi.android.ting.utilities.view.ViewResolver;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ViewSwitcher;

public class BoardViewResolver extends ViewResolver<Board> {

	@Inject
	public IImageLoader imageLoader;

	@Inject
	public BoardController controller;

	private boolean showControls;

	public BoardViewResolver(Context activity, boolean showControls) {
		super(R.layout.board_listitem_layout, activity);
		this.showControls = showControls;
	}

	@Override
	protected void decorateView(View view, Board board, ViewGroup parent) {
		TextView headerText = (TextView) this.findViewById(view,
				R.id.textview_board_title);
		TextView descriptionTest = (TextView) this.findViewById(view,
				R.id.textview_board_description);
		headerText.setText(board.getTitle());
		descriptionTest.setText(board.getDescription());

		if (this.showControls) {
			this.setupControls(view, board);
		}

	}

	private void setupControls(View view, final Board board) {
		final ViewSwitcher switcher = (ViewSwitcher) this.findViewById(view,
				R.id.viewswitcher_board_controls_switcher);

		final ToggleButton followToggle = (ToggleButton) this.findViewById(
				view, R.id.togglebutton_follow_board);

		this.controller.setup(new IBoardView() {

			@Override
			public void showOwnerState() {
				switcher.setDisplayedChild(1);
				switcher.setVisibility(View.VISIBLE);
			}

			@Override
			public void showDefaultState(BoardAffiliation affiliation) {
				switcher.setDisplayedChild(0);
				followToggle
						.setChecked(affiliation == BoardAffiliation.Follower);
				followToggle.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						boolean follow = ((CompoundButton) v).isChecked();
						boolean success = BoardViewResolver.this.controller
								.setFollowBoard(board, follow);
						if (!success)
							followToggle.setChecked(false);
					}
				});
				switcher.setVisibility(View.VISIBLE);
			}

			@Override
			public Context getContext() {
				return context;
			}

			@Override
			public void displayBoardInfo(Board result) {
				// this already happened				
			}

		}, board);
	}

	private void addContributor(LinearLayout contributorsLayout, Board board,
			ViewGroup parent) {
		List<User> contributors = board.getContributors();

		if (contributors != null && contributorsLayout.getChildCount() >= 4
				&& contributors.size() >= 4) {
			// TODO: some kind of indication there are more contributors

		} else {
			LayoutInflater inflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			ImageView image = (ImageView) inflater.inflate(
					R.layout.contributor_small_layout, parent, false);
			if (contributorsLayout.getChildCount() == 0) {
				if (board.getOwner() != null) {
					this.imageLoader.loadImage(board.getOwner()
							.getProfilePictureUri(), image);
				}
			} else {
				int contributorNumber = contributorsLayout.getChildCount() - 1;
				if (contributors != null) {
					if (contributors.size() >= 0
							&& contributorNumber < contributors.size()) {
						User contributor = contributors.get(contributorNumber);
						this.imageLoader.loadImage(
								contributor.getProfilePictureUri(), image);
					}
				}
			}
			contributorsLayout.addView(image);
		}
	}
}
