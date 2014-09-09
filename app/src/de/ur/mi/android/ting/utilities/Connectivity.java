package de.ur.mi.android.ting.utilities;

import javax.inject.Singleton;

import de.ur.mi.android.ting.R;
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

		NetworkInfo netInfo = this.cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		if (notifyOnError) {
			this.notifyMissingConnection();
		}
		return false;
	}

	private void notifyMissingConnection() {
		NotifyKind kind = NotifyKind.ERROR;
		Notify.current().showDialog(R.string.connection_error_title, R.string.connection_error_content, kind);
	}

}
