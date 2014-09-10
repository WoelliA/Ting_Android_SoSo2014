package de.ur.mi.android.ting.app.activities;

import javax.inject.Inject;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.adapters.ViewCreationDelegatingListAdapter;
import de.ur.mi.android.ting.app.controllers.UserDetailsController;
import de.ur.mi.android.ting.app.viewResolvers.SearchResultResolvers.BoardResolver;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.User;
import de.ur.mi.android.ting.utilities.IImageLoader;
import de.ur.mi.android.ting.utilities.view.ViewResolver;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class UserDetailsActivity extends BaseActivity implements
		IUserDetailsView {

	public static final String USER_ID_KEY = "userId";
	
	@Inject
	public UserDetailsController controller;

	@Inject
	public IImageLoader imageLoader;

	private ListView listView;
	private ViewCreationDelegatingListAdapter<Board> adapter;
	private View headerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_userdetails);
		this.listView = (ListView) this.findViewById(R.id.list);
		ViewResolver<Board> viewResolver = new BoardResolver(this);
		this.adapter = new ViewCreationDelegatingListAdapter<Board>(this,
				viewResolver);
		this.listView.setAdapter(this.adapter);

		this.headerView = this.getLayoutInflater().inflate(
				R.layout.user_details_header_view, null, false);

		this.listView.addHeaderView(this.headerView);

		String userId = this.getIntent().getExtras().getString(USER_ID_KEY);
		
		this.controller.setView(this,userId);
	}

	@Override
	public void setProfileInfo(User user) {
		ImageView iamge = (ImageView) this.headerView
				.findViewById(R.id.profile_imageview);
		this.imageLoader.loadImage(user.getProfilePictureUri(), iamge);
		
		TextView nameTextView = (TextView) this.headerView.findViewById(R.id.profile_name);
		nameTextView.setText(user.getName());
	}

	@Override
	public void add(Object item) {
		this.adapter.add((Board) item);
	}

	@Override
	public void addAll(Object[] items) {
		for (Object object : items) {
			this.add(object);
		}
	}

}
