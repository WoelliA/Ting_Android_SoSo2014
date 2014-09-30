package de.ur.mi.android.ting.app.receiver;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.activities.MainActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class WiFiReceiver extends BroadcastReceiver{

	
	private ConnectivityManager connectivityManager;
	private NetworkInfo wifiInfo;

	@Override
	public void onReceive(Context context, Intent intent) {
		
	    try {
	    	
	    	connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	        wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		    if (wifiInfo.isConnected()) {
		            makeNotification(context);
		              }
			
		} finally {
		}    
		
	}
	
	private void makeNotification(Context context) {
		String contentTitle = context.getApplicationContext().getResources().getString(R.string.notificationContentTitle);
		String contentText = context.getApplicationContext().getResources().getString(R.string.notificationContentText);
		
		Notification notification = new Notification.Builder(context.getApplicationContext())
		 	.setContentTitle(contentTitle)
		 	.setContentText(contentText)
		 	.setSmallIcon(R.drawable.ic_launcher)
		 	.build();

		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		
		Intent notificationIntent = new Intent(context, MainActivity.class);
		PendingIntent nI = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		notification.contentIntent = nI;
		
		NotificationManager notifManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		notifManager.notify(1, notification);
	}
	
}
