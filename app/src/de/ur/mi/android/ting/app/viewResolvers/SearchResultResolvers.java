package de.ur.mi.android.ting.app.viewResolvers;

import java.util.List;

import javax.inject.Inject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.User;
import de.ur.mi.android.ting.utilities.IImageLoader;
import de.ur.mi.android.ting.utilities.view.ViewResolver;

public class SearchResultResolvers {
	public static class PinSearchResultViewResolver extends PinListViewResolver {

		public PinSearchResultViewResolver(Context context) {
			super(context);
			
		}
	}

	public static class BoardResolver extends ViewResolver<Board> {

		@Inject
		public IImageLoader imageLoader;

		public BoardResolver(Context context) {
			super(R.layout.search_board_result_layout, context);
		}

		@Override
		protected void decorateView(View view, Board board, ViewGroup parent) {
			LinearLayout contributorsLayout = (LinearLayout) this.findViewById(
					view, R.id.board_result_contributors);
			TextView boardTitle = (TextView) this.findViewById(view,
					R.id.board_result_title);

			this.addContributor(contributorsLayout, board, parent);
			boardTitle.setText(board.getTitle());
		}

		private void addContributor(LinearLayout contributorsLayout,
				Board board, ViewGroup parent) {
			List<User> contributors = board.getContributors();

			if (contributorsLayout.getChildCount() >= 4
					&& contributors.size() >= 4) {
				// TODO: some kind of indication there are more contributors

			} else {
				LayoutInflater inflater = (LayoutInflater) this.context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				ImageView image = (ImageView) inflater.inflate(
						R.layout.contributor_small_layout, parent, false);
				if (contributorsLayout.getChildCount() == 0) {
					this.imageLoader.loadImage(board.getOwner()
							.getProfilePictureUri(), image);
				} else {
					int contributorNumber = contributorsLayout.getChildCount() - 1;
					if (contributors.size() >= 0
							&& contributorNumber < contributors.size()) {
						User contributor = contributors.get(contributorNumber);
						this.imageLoader.loadImage(
								contributor.getProfilePictureUri(), image);
					}
				}
				contributorsLayout.addView(image);
			}
		}
	}

	public static class UserResolver extends ViewResolver<User> {

		@Inject
		IImageLoader imageLoader;

		public UserResolver(Context context) {
			super(R.layout.search_user_result_layout, context);
		}

		@Override
		protected void decorateView(View view, User user, ViewGroup parent) {
			ImageView profileImage = (ImageView) this.findViewById(view,
					R.id.user_profile_picture);
			TextView profileName = (TextView) this.findViewById(view,
					R.id.user_profile_name);

			this.imageLoader.loadImage(user.getProfilePictureUri(), profileImage);
			profileName.setText(user.getName());
		}

	}
}
