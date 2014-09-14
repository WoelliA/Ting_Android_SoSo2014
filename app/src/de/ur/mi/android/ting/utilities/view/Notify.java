package de.ur.mi.android.ting.utilities.view;

import java.util.List;

import javax.security.auth.callback.ConfirmationCallback;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.TingApp;

public class Notify implements INotify {

	private static Notify current;
	private Context context;

	public Notify(Context context) {
		this.context = context;
		current = this;
	}

	private boolean canNotify() {
		this.context = TingApp.getActivityContext();
		return TingApp.isVisible();
	}

	@Override
	public void show(int titleResourceId, int contentResourceId, NotifyKind kind) {
		if (!canNotify()) {
			return;
		}
		if (kind == NotifyKind.SUCCESS) {
			showToast(titleResourceId);
			return;
		}
		AlertDialog.Builder dialogDialog = new AlertDialog.Builder(this.context);

		dialogDialog.setTitle(this.context.getString(titleResourceId));
		if (contentResourceId > 1) {
			dialogDialog.setMessage(this.context.getString(contentResourceId));
		}
		dialogDialog.setNeutralButton(R.string.button_dismiss,
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		dialogDialog.setCancelable(true);
		int drawableId = this.getDrawableId(kind);
		dialogDialog.setIcon(drawableId);
		dialogDialog.show();
	}

	@Override
	public void showToast(int textResourceId) {
		this.showToast(context.getString(textResourceId));
	}

	@Override
	public void showToast(String content) {
		if (!canNotify())
			return;
		Toast toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
		toast.show();
	}

	private int getDrawableId(NotifyKind kind) {
		switch (kind) {
		case ERROR:
			return android.R.drawable.ic_dialog_alert;
		case INFO:
			return android.R.drawable.ic_dialog_info;
		default:
			return android.R.drawable.ic_dialog_info;
		}
	}

	public static Notify current() {
		return current;
	}

	@Override
	public LoadingContext showLoading(int titleResourceId) {
		return new LoadingContext(this.context, titleResourceId);
	}

	public class LoadingContext {
		private ProgressDialog dialog;

		public LoadingContext(Context context, int titleResourceId) {
			if (canNotify()) {
				this.dialog = new ProgressDialog(context);
				if (titleResourceId > 0)
					this.dialog.setTitle(titleResourceId);
				this.dialog.setCancelable(false);
				this.dialog.show();
			}
		}

		public void close() {
			if (dialog != null)
				this.dialog.cancel();
		}

	}

	public void showYesNoDialog(int titleResId, int contentResId,
			int yesButtonTextResId, final IYesNoCallback callback) {
		if (!canNotify())
			return;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(titleResId);
		if (contentResId > 0) {
			builder.setMessage(contentResId);
		}

		builder.setPositiveButton(yesButtonTextResId,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						callback.onYes();
					}
				}).setNegativeButton(android.R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						callback.onNo();
					}
				});
		// Create the AlertDialog object and return it
		builder.create().show();

	}
}
