package de.ur.mi.android.ting.app;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;
import de.ur.mi.android.ting.R;

public class ProximityReceiver extends BroadcastReceiver{


	@Override
	public void onReceive(Context context, Intent intent) {

		String key = LocationManager.KEY_PROXIMITY_ENTERING;
		boolean entering = intent.getBooleanExtra(key, false);
		
		if(entering){
			Toast.makeText(context.getApplicationContext(), "entering", Toast.LENGTH_LONG).show();
			
			String contentTitle = context.getApplicationContext().getResources().getString(R.string.notificationContentTitle);
			String contentText = context.getApplicationContext().getResources().getString(R.string.notificationContentText);
			
			Notification notification = new Notification.Builder(context)
	         	.setContentTitle(contentTitle)
	         	.setContentText(contentText)
	         	.setSmallIcon(R.drawable.ic_launcher)
	         	.build();

			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notification.defaults |= Notification.DEFAULT_VIBRATE;
			
			NotificationManager notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			notifManager.notify(1, notification);

		}
		

		
	}


	
	
}
