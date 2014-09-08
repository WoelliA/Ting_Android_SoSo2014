package de.ur.mi.android.ting.utilities;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.IInjector;
import de.ur.mi.android.ting.utilities.view.INotify;
import de.ur.mi.android.ting.utilities.view.Notify;
import de.ur.mi.android.ting.utilities.view.NotifyKind;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

@Singleton
public class Connectivity implements IConnectivity {

	private Context context;

	private ConnectivityManager cm;

	public Connectivity(Context context) {
		this.context = context;
		this.cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

	}

	@Override
	public boolean hasWebAccess(boolean notifyOnError) {

		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		if (notifyOnError) {
			this.notifyMissingConnection();
		}
		return false;
	}

	private void notifyMissingConnection() {
		String title = context.getString(R.string.connection_error_title);
		String content = context.getString(R.string.connection_error_content);
		NotifyKind kind = NotifyKind.ERROR;
		Notify.current().notify(title, content, kind);
	}

}
