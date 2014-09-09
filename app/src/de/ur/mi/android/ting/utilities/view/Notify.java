package de.ur.mi.android.ting.utilities.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import de.ur.mi.android.ting.R;

public class Notify implements INotify {

	private static INotify current;
	private Context context;

	public Notify(Context context) {
		this.context = context;
		current = this;
	}

	@Override
	public void notify(String title, String content, NotifyKind kind) {
		AlertDialog.Builder dialogDialog = new AlertDialog.Builder(this.context);
		dialogDialog.setTitle(title);
		dialogDialog.setMessage(content);
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

	public static INotify current() {
		return current;
	}
	
	
	@Override
	public LoadingContext notifyLoading(int titleResourceId) {
		return new LoadingContext(this.context, titleResourceId);					
	}
	
	public class LoadingContext{
		private ProgressDialog dialog;

		public LoadingContext(Context context, int titleResourceId) {
			this.dialog = new ProgressDialog(context);
			this.dialog.setTitle(titleResourceId);
			this.dialog.setCancelable(false);
			this.dialog.show();
		}

		public void close() {
			this.dialog.cancel();			
		}
		
	}

}
