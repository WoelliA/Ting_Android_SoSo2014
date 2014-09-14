package de.ur.mi.android.ting.app.controllers;

import android.content.Context;
import android.content.Intent;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.activities.LoginActivity;
import de.ur.mi.android.ting.utilities.view.Notify;
import de.ur.mi.android.ting.utilities.view.SimpleYesNoCallback;

public class NotifyRequiresLogin {

	private Context context;
	private int contentResId;

	public NotifyRequiresLogin(Context context, int contentResId) {
		this.context = context;
		this.contentResId = contentResId;
	}

	public void show() {
		Notify.current().showYesNoDialog(R.string.action_login,
				contentResId, R.string.action_login,
				new SimpleYesNoCallback() {

					@Override
					public void onYes() {
						Intent intent = new Intent(context, LoginActivity.class);
						context.startActivity(intent);
					}
				});
	}
}
