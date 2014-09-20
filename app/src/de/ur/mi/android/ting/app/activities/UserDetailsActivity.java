package de.ur.mi.android.ting.app.activities;

import javax.inject.Inject;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.adapters.ViewCreationDelegatingListAdapter;
import de.ur.mi.android.ting.app.controllers.UserDetailsController;
import de.ur.mi.android.ting.app.viewResolvers.SearchResultResolvers.BoardResolver;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.User;
import de.ur.mi.android.ting.utilities.IImageLoader;
import de.ur.mi.android.ting.utilities.view.ViewResolver;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
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

	@Inject
	public LocalUser localUser;

	private ListView listView;
	private ViewCreationDelegatingListAdapter<Board> adapter;
	private View headerView;

	private boolean isLocalUserPage;

	private String userId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.userId = this.getIntent().getExtras().getString(USER_ID_KEY);
		this.isLocalUserPage = this.userId.equals(this.localUser.getId());
		this.initUi();

	}
	

	@Override
	protected void onStart() {
		super.onStart();
		this.controller.setView(this, this.userId);
	}

	private void initUi() {
		this.setContentView(R.layout.activity_userdetails);
		this.listView = (ListView) this.findViewById(R.id.list);
		this.listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				position = position - ((ListView) arg0).getHeaderViewsCount();
				if (position < 0) {
					return;
				}
				Board board = UserDetailsActivity.this.adapter
						.getItem(position);
				Intent intent = new Intent(UserDetailsActivity.this,
						BoardDetailsActivity.class);
				intent.putExtra(BoardDetailsActivity.BOARD_ID_KEY,
						board.getId());
				UserDetailsActivity.this.startActivity(intent);
			}
		});
		ViewResolver<Board> viewResolver = new BoardResolver(this);

		this.headerView = this.getLayoutInflater().inflate(
				R.layout.user_details_header_view, null, false);
		this.listView.addHeaderView(this.headerView, null, false);

		this.adapter = new ViewCreationDelegatingListAdapter<Board>(this,
				viewResolver);
		this.listView.setAdapter(this.adapter);


		if (this.isLocalUserPage) {
			this.initLocalUserControls();
		}
	}

	private void initLocalUserControls() {
		Button editButton = (Button) this.headerView
				.findViewById(R.id.button_edit_profile);
		editButton.setVisibility(View.VISIBLE);

		editButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserDetailsActivity.this,
						EditProfileActivity.class);
				UserDetailsActivity.this.startActivity(intent);
			}
		});

		Button createBoardButton = (Button) this.headerView
				.findViewById(R.id.button_create_board);
		createBoardButton.setVisibility(View.VISIBLE);

		createBoardButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserDetailsActivity.this,
						EditBoardActivity.class);
				UserDetailsActivity.this.startActivity(intent);
			}
		});
	}

	@Override
	public void setProfileInfo(User user) {
		ImageView iamge = (ImageView) this.headerView
				.findViewById(R.id.profile_imageview);
		this.imageLoader.loadImage(user.getProfilePictureUri(), iamge);

		TextView nameTextView = (TextView) this.headerView
				.findViewById(R.id.profile_name);
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

	@Override
	public void set(Object[] items) {
		this.adapter.clear();
		this.addAll(items);

	}

}
