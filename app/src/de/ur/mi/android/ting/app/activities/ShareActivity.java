package de.ur.mi.android.ting.app.activities;

import java.util.ArrayList;
import javax.inject.Inject;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.ISelectedListener;
import de.ur.mi.android.ting.app.controllers.ShareController;
import de.ur.mi.android.ting.app.controllers.IShareSetupView;
import de.ur.mi.android.ting.app.fragments.EditPinDetailsFragment;
import de.ur.mi.android.ting.app.fragments.ListFragment;
import de.ur.mi.android.ting.app.fragments.SelectBoardFragment;
import de.ur.mi.android.ting.app.fragments.SelectPinImageFragment;
import de.ur.mi.android.ting.model.PinData;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.utilities.LoadedImageData;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

public class ShareActivity extends FragmentActivityBase implements
		IShareSetupView {

	@Inject
	ShareController controller;
	private ShareStage currentShareStage;
	private Fragment stageFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_share);

		this.controller.setView(this);

		// Get intent, action and MIME type
		Intent intent = this.getIntent();
		String action = intent.getAction();
		String type = intent.getType();

		if (Intent.ACTION_SEND.equals(action) && type != null) {
			if ("text/plain".equals(type)) {
				this.controller.handleSendText(intent
						.getStringExtra(Intent.EXTRA_TEXT));
			} else if (type.startsWith("image/")) {
				this.controller.handleSendImage((Uri) intent
						.getParcelableExtra(Intent.EXTRA_STREAM));
			}
		} else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
			if (type.startsWith("image/")) {
				ArrayList<Uri> imageUris = intent
						.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
				this.controller.handleSendMultipleImages(imageUris);
			}
		} else {
			// Handle other intents, such as being started from the home screen
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		this.goToShareStage(ShareStage.ImageSelect, false);
	}

	@Override
	public void addDisplayPinnableImage(PinData pinData) {
		this.ensureShareStage(ShareStage.ImageSelect);
		((ListFragment<?>) this.stageFragment).add(pinData);
	}

	private void ensureShareStage(ShareStage stage) {
		if (this.currentShareStage != stage) {
			this.goToShareStage(stage, false);
		}
	}

	private void goToShareStage(ShareStage shareStage, boolean addToBackstack) {
		this.currentShareStage = shareStage;
		this.stageFragment = this.getFragmentForShareStage(shareStage);
		this.displayFragment(shareStage, this.stageFragment, addToBackstack);
	}

	private void displayFragment(ShareStage stage, Fragment stageFragment,
			boolean addToBackstack) {
		FragmentTransaction transaction = this.getSupportFragmentManager()
				.beginTransaction()
				.add(R.id.share_activity_frame, stageFragment);
		if (addToBackstack) {
			transaction.addToBackStack(stage.toString());
		}
		transaction.setTransition(android.R.anim.fade_in);
		transaction.commit();
	}

	private Fragment getFragmentForShareStage(ShareStage shareStage) {
		switch (shareStage) {
		case ImageSelect:
			SelectPinImageFragment selectPinImageFragment = new SelectPinImageFragment();
			selectPinImageFragment
					.setSelectListener(new ISelectedListener<PinData>() {

						@Override
						public void onSelected(PinData selectedItem) {
							ShareActivity.this.controller.onPinImageSelected(selectedItem);
							ShareActivity.this.goToShareStage(ShareStage.BoardSelect, true);
						}
					});
			return selectPinImageFragment;
		case BoardSelect:
			SelectBoardFragment selectBoardFragment = new SelectBoardFragment();
			selectBoardFragment
					.setSelectListener(new ISelectedListener<Board>() {

						@Override
						public void onSelected(Board selectedBoard) {
							ShareActivity.this.controller.onBoardSelected(selectedBoard);
							ShareActivity.this.goToShareStage(ShareStage.Details, true);
						}
					});
			return selectBoardFragment;

		case Details:
			EditPinDetailsFragment fragment = new EditPinDetailsFragment();
			fragment.setOnSendPinClickListener(new SimpleDoneCallback<PinData>() {

				@Override
				public void done(PinData result) {
					ShareActivity.this.controller.createPin(result);
				}
			});
			fragment.setPinData(this.controller.getPinData());
			return fragment;
		default:
			return null;
		}
	}

	@Override
	public void displayError(int errorCode) {
		// TODO Auto-generated method stub

	}

	private enum ShareStage {
		ImageSelect, BoardSelect, Details
	}

}
