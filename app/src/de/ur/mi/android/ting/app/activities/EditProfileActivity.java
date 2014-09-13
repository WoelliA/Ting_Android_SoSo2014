package de.ur.mi.android.ting.app.activities;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.security.auth.callback.ConfirmationCallback;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.controllers.EditProfileController;
import de.ur.mi.android.ting.app.controllers.EditProfileController.EditProfileResult;
import de.ur.mi.android.ting.app.controllers.EditProfileController.EditProfileView;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.User;
import de.ur.mi.android.ting.utilities.IImageLoader;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;
import de.ur.mi.android.ting.utilities.view.Notify;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.support.v4.content.FileProvider;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_edit_profile);
		this.initUi();
		controller.setView(this);
	}

	@Override
	public void onBackPressed() {
		onBackPressed(false);
	}

	private void onBackPressed(boolean force) {
		if (force || controller.hasNotChanged(getEditProfileResult())) {
			super.onBackPressed();
			return;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.dialog_unsaved_changes_title);
		builder.setMessage(R.string.dialog_unsaved_changes_content)
				.setPositiveButton(R.string.button_proceed_text,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								onBackPressed(true);
							}
						})
				.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {
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
				controller.onProfileImageChanged(imageUri);
			}
		}
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
		imageloader.loadImage(user.getProfilePictureUri(),
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
			startActivityForResult(intent, SELECT_PHOTO);
			break;
		case R.id.button_save:
			EditProfileResult editProfileResult = getEditProfileResult();
			if (editProfileResult.getName().trim().length() == 0) {
				this.usernameView.setError(this
						.getString(R.string.error_field_required));
				return;
			}

			controller.onSaveProfile(editProfileResult);
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
}
