package de.ur.mi.android.ting.app.controllers;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.net.Uri;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.User;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.IImageLoader;
import de.ur.mi.android.ting.utilities.LoadIndicatingNotifyingCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;
import de.ur.mi.android.ting.utilities.view.Notify;

public class EditProfileController {
	public static class EditProfileResult extends User {

		private byte[] imageBytes;
		private String email;

		public EditProfileResult(String name, String email, String info) {
			super("", name, info, "");
			this.email = email;
		}

		private void setNewProfileImage(byte[] imageBytes) {
			this.imageBytes = imageBytes;
		}

		public byte[] getNewProfileImage() {
			return this.imageBytes;
		}

		public String getEmail() {
			return this.email;
		}
	}

	public static interface EditProfileView {

		void displayProfileImage(Bitmap result);

		void displayUserProfile(User user, String email);
	}

	private LocalUser user;
	private IImageLoader imageloader;
	private IUserService userService;
	private EditProfileView view;
	protected Bitmap newProfileImage;

	public EditProfileController(IImageLoader imageloader,
			IUserService userService, LocalUser user) {
		this.imageloader = imageloader;
		this.userService = userService;
		this.user = user;
	}

	public void setView(EditProfileView view) {
		this.view = view;
		if (this.user.isNew()) {
			this.user.setName("");
		}
		this.view.displayUserProfile(this.user, this.user.getEmail());
	}

	public void onProfileImageChanged(Uri imageUri) {
		this.imageloader.loadImage(imageUri.toString(),
				new SimpleDoneCallback<Bitmap>() {

					@Override
					public void done(Bitmap result) {
						EditProfileController.this.newProfileImage = result;
						EditProfileController.this.view
								.displayProfileImage(result);
					}
				});

	}

	public boolean onSaveProfile(final EditProfileResult editProfileResult, final IDoneCallback<Void> callback) {
		if (this.hasNotChanged(editProfileResult)) {
			Notify.current().showToast(R.string.success_saving_profile);
			return false;
		}
		if (this.newProfileImage != null) {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			this.newProfileImage.compress(Bitmap.CompressFormat.PNG, 100,
					stream);
			byte[] byteArray = stream.toByteArray();
			editProfileResult.setNewProfileImage(byteArray);
		}

		this.userService.saveChangedUser(editProfileResult,
				new LoadIndicatingNotifyingCallback<Void>(
						R.string.success_saving_profile) {
					@Override
					public void done(Void result) {
						super.done(result);
						callback.done(null);
						EditProfileController.this.user.setInfo(
								new User("", editProfileResult.getName(),
										editProfileResult.getInfo(), ""),
								editProfileResult.getEmail());
						EditProfileController.this.newProfileImage = null;
					}
				});

		return true;
	}

	public boolean hasNotChanged(EditProfileResult editProfileResult) {
		return this.user.getName().equals(editProfileResult.getName())
				&& this.user.getEmail().equals(editProfileResult.getEmail())
				&& this.newProfileImage == null
				&& editProfileResult.getInfo().equals(this.user.getInfo());
	}

}
