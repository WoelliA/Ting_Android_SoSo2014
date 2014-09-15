package de.ur.mi.android.ting.app.viewResolvers;

import javax.inject.Inject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.model.primitives.User;
import de.ur.mi.android.ting.utilities.IImageLoader;
import de.ur.mi.android.ting.utilities.view.ViewResolver;

public class SearchResultResolvers {
	public static class PinSearchResultViewResolver extends PinViewResolver {

		public PinSearchResultViewResolver(Context context) {
			super(context);
			
		}
	}

	public static class BoardResolver extends BoardViewResolver {


		public BoardResolver(Context context) {
			super(context, true);
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

			TextView infoView = (TextView) this.findViewById(view, R.id.textview_info);
			
			infoView.setText(user.getInfo());
			
			this.imageLoader.loadImage(user.getProfilePictureUri(), profileImage);
			profileName.setText(user.getName());
		}

	}
}
