package de.ur.mi.android.ting.app.activities;

import java.io.ObjectOutputStream.PutField;

import javax.inject.Inject;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.controllers.EditProfileController;
import de.ur.mi.android.ting.app.controllers.EditProfileController.EditProfileResult;
import de.ur.mi.android.ting.app.controllers.EditProfileController.EditProfileView;
import de.ur.mi.android.ting.model.primitives.User;
import de.ur.mi.android.ting.utilities.IImageLoader;
import de.ur.mi.android.ting.utilities.view.Notify;
import de.ur.mi.android.ting.utilities.view.NotifyKind;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class EditProfileActivity extends BaseActivity implements
		EditProfileView, OnClickListener {

	protected static final int SELECT_PHOTO = 1;
	private EditText usernameView;
	private ImageView profileimageView;

	@Inject
	public EditProfileController controller;

	@Inject
	public IImageLoader imageloader;
	private EditText emailView;
	private EditText infoView;
	private boolean isTutorial;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_edit_profile);

		Intent intent = getIntent();
		isTutorial = (boolean) intent.getBooleanExtra(Constants.ISTUTORIAL_KEY,
				false);

		this.initUi();
		this.controller.setView(this);
	}

	@Override
	public void onBackPressed() {
		this.onBackPressed(false);
	}

	private void onBackPressed(boolean force) {
		if (force || this.controller.hasNotChanged(this.getEditProfileResult())) {
			super.onBackPressed();
			return;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.dialog_unsaved_changes_title);
		builder.setMessage(R.string.dialog_unsaved_changes_content)
				.setPositiveButton(R.string.button_proceed_text,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								EditProfileActivity.this.onBackPressed(true);
							}
						})
				.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {

							}
						});
		// Create the AlertDialog object and return it
		builder.create().show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SELECT_PHOTO) {
			if (resultCode == RESULT_OK) {
				final Uri imageUri = data.getData();
				this.controller.onProfileImageChanged(imageUri);
			}
		}
	}

	private boolean navigate() {
		if (isTutorial) {
			Intent intent = new Intent(this, EditBoardActivity.class);
			intent.putExtra(Constants.ISTUTORIAL_KEY, true);
			this.startActivity(intent);
			return true;
		}
		return false;
	}

	private void initUi() {
		this.usernameView = (EditText) this
				.findViewById(R.id.edittext_username);
		this.profileimageView = (ImageView) this
				.findViewById(R.id.imageview_profile_image);
		this.emailView = (EditText) this.findViewById(R.id.edittext_email);
		this.infoView = (EditText) this.findViewById(R.id.edittext_info);

		((Button) this.findViewById(R.id.button_change_image))
				.setOnClickListener(this);
		((Button) this.findViewById(R.id.button_save)).setOnClickListener(this);

	}

	@Override
	public void displayProfileImage(Bitmap result) {
		this.profileimageView.setImageBitmap(result);
	}

	@Override
	public void displayUserProfile(User user, String email) {
		this.imageloader.loadImage(user.getProfilePictureUri(),
				this.profileimageView);
		this.usernameView.setText(user.getName());
		this.emailView.setText(email);
		this.infoView.setText(user.getInfo());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_change_image:
			Intent intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.setType("image/*");
			this.startActivityForResult(intent, SELECT_PHOTO);
			break;
		case R.id.button_save:

			EditProfileResult editProfileResult = this.getEditProfileResult();
			if (editProfileResult.getName().trim().length() == 0) {
				this.usernameView.setError(this
						.getString(R.string.error_field_required));
				return;
			}

			this.controller.onSaveProfile(editProfileResult);
			navigate();
			break;
		}
	}

	private EditProfileResult getEditProfileResult() {
		String username = this.usernameView.getText().toString();
		String email = this.emailView.getText().toString();
		String info = this.infoView.getText().toString();
		EditProfileResult editProfileResult = new EditProfileResult(username,
				email, info);
		return editProfileResult;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (isTutorial) {
			Notify.current().show(R.string.welcome_dialog_title,
					R.string.welcome_dialog_edit_profile, NotifyKind.INFO);
		}
	}
}
