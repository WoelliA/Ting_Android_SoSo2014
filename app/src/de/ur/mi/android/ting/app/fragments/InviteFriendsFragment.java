package de.ur.mi.android.ting.app.fragments;

import com.facebook.widget.FacebookDialog;
import com.facebook.widget.FacebookDialog.MessageDialogBuilder;

import de.ur.mi.android.ting.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class InviteFriendsFragment extends FragmentBase implements
		OnClickListener {

	private MessageDialogBuilder builder;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_invite_friends, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.initUi();
	}

	private void initUi() {
		initFb();

	}

	private void initFb() {
		Button fbButton = (Button) this
				.findViewById(R.id.action_invite_facebook);

		if (FacebookDialog.canPresentMessageDialog(getActivity())) {
			fbButton.setOnClickListener(this);
			createFbBuilder();
		} else {
			fbButton.setEnabled(false);
			fbButton.setText("NO!");
		}
	}

	private void createFbBuilder() {
		builder = new FacebookDialog.MessageDialogBuilder(getActivity())
				.setLink("https://developers.facebook.com/docs/android/share/")
				.setName("Message Dialog Tutorial")
				.setCaption("Build great social apps that engage your friends.")
				.setPicture("http://i.imgur.com/g3Qc1HN.png")
				.setDescription(
						"Allow your users to message links from your app using the Android SDK.")
				.setFragment(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.action_invite_facebook:
			FacebookDialog dialog = builder.build();
			dialog.present();
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected boolean skipInject() {
		return true;
	}
}
