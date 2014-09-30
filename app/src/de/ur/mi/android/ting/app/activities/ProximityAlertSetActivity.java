package de.ur.mi.android.ting.app.activities;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.FlagToString;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.receiver.ProximityReceiver;

public class ProximityAlertSetActivity extends Activity{

	private TextView eTLatitude;
	private TextView eTLongitude;
	
	private LocationManager locationManager;
	private LocationListener locationListener;
	
	private static DecimalFormat nf;
	private static final long MIN_UPDATE_TIME = 6000;// millsec
	private static final float MIN_UPDATE_DISTANCE = 1;// meter(s)
	private static final String PROX_ALERT_URI = "de.ur.mi.android.intent.action.PROX_ALERT";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_set_proximity);
	
		this.initNumberFormat();
		this.initLocationManager();
		this.initProximityReceiver();
		this.initUI();
		
	}
	
	
	private void initProximityReceiver() {
		IntentFilter filter = new IntentFilter(PROX_ALERT_URI);
		registerReceiver(new ProximityReceiver(), filter);
	}


	private void initNumberFormat() {
		nf = new DecimalFormat("##.########");		
		DecimalFormatSymbols custom = new DecimalFormatSymbols();
		custom.setDecimalSeparator('.');
		nf.setDecimalFormatSymbols(custom);
		
	}


	private void initLocationManager() {
		this.initLocationListener();
		this.locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_UPDATE_TIME, MIN_UPDATE_DISTANCE, this.locationListener);
	}




	private void initLocationListener() {
		this.locationListener = new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location location) {
				setTextViewsToLastKnownLocation();
				
			}
		};
		
	}


	private void initUI() {
		this.initTextViews();
		this.initAddButton();
		
	}




	private void initTextViews() {
		this.eTLatitude = (TextView)this.findViewById(R.id.current_latitude);
		this.eTLongitude = (TextView)this.findViewById(R.id.current_longitude);
		setTextViewsToLastKnownLocation();
	}


	protected void setTextViewsToLastKnownLocation() {
		Location location = this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(location!=null){
			this.eTLatitude.setText(nf.format(location.getLatitude()));
			this.eTLongitude.setText(nf.format(location.getLongitude()));
		} else {
			Toast.makeText(ProximityAlertSetActivity.this,this.getResources().getString(R.string.no_location), Toast.LENGTH_SHORT).show();
		}
		
	}




	private void initAddButton() {
		Button saveReminder = (Button)this.findViewById(R.id.button_save_reminder);
		saveReminder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String latitude = ProximityAlertSetActivity.this.eTLatitude.getText().toString();
				String longitude = ProximityAlertSetActivity.this.eTLongitude.getText().toString();
				if (latitude.equals("") || longitude.equals("")) {
					Toast.makeText(ProximityAlertSetActivity.this,ProximityAlertSetActivity.this.getResources().getString(R.string.fill_in), Toast.LENGTH_SHORT).show();
					
				} else {
					addProxAlert();
					Toast.makeText(ProximityAlertSetActivity.this,"saved", Toast.LENGTH_SHORT).show();
					
					testAlert();	
					
				}
				
			}
		
		});
	
	}
	
	
    private void addProxAlert() {
    	
		Location location = ProximityAlertSetActivity.this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    	
    	Intent locationReached = new Intent();
    	locationReached.setAction(PROX_ALERT_URI);
    	PendingIntent proximityIntent = PendingIntent.getBroadcast(this, 0, locationReached, 0);
	         
    	locationManager.addProximityAlert(location.getLatitude(), location.getLongitude(), 5, -1, proximityIntent);
    	
}


	private void testAlert() {
		String contentTitle = getApplicationContext().getResources().getString(R.string.notificationContentTitle);
		String contentText = getApplicationContext().getResources().getString(R.string.notificationContentText);
		
		Notification notification = new Notification.Builder(getApplicationContext())
		 	.setContentTitle(contentTitle)
		 	.setContentText(contentText)
		 	.setSmallIcon(R.drawable.ic_launcher)
		 	.build();

		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		
		Intent notificationIntent = new Intent();
		PendingIntent nI = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		notification.contentIntent = nI;
		
		NotificationManager notifManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		notifManager.notify(1, notification);
	}
	
}
