package de.ur.mi.android.ting.app.activities;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import de.ur.mi.android.ting.R;

public class ProximityAlertSetActivity extends Activity{

	private TextView eTLatitude;
	private TextView eTLongitude;
	
	private LocationManager locationManager;
	private LocationListener locationListener;
	
	private static DecimalFormat nf;
	private static final long MIN_UPDATE_TIME = 6000;// millsec
	private static final float MIN_UPDATE_DISTANCE = 1;// meter(s)
	private static final float PROXIMITY_RADIUS = 10;
	private static final String PROX_ALERT_URI = "de.ur.mi.android.intent.action.PROX_ALERT";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_proximity);
	
		initNumberFormat();
		initLocationManager();
		initUI();
		
	}

	private void initNumberFormat() {
		nf = new DecimalFormat("##.########");		
		DecimalFormatSymbols custom = new DecimalFormatSymbols();
		custom.setDecimalSeparator('.');
		nf.setDecimalFormatSymbols(custom);
		
	}


	private void initLocationManager() {
		initLocationListener();
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_UPDATE_TIME, MIN_UPDATE_DISTANCE, this.locationListener);
	}




	private void initLocationListener() {
		locationListener = new LocationListener() {
			
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
		initTextViews();
		initAddButton();
		
	}




	private void initTextViews() {
		eTLatitude = (TextView)this.findViewById(R.id.current_latitude);
		eTLongitude = (TextView)this.findViewById(R.id.current_longitude);
		setTextViewsToLastKnownLocation();
	}


	protected void setTextViewsToLastKnownLocation() {
		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(location!=null){
			eTLatitude.setText(nf.format(location.getLatitude()));
			eTLongitude.setText(nf.format(location.getLongitude()));
		} else {
			Toast.makeText(ProximityAlertSetActivity.this,this.getResources().getString(R.string.no_location), Toast.LENGTH_SHORT).show();
		}
		
	}




	private void initAddButton() {
		Button saveReminder = (Button)findViewById(R.id.button_save_reminder);
		saveReminder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
					addProxAlert();
					
				
			}
		
		});
	
	}
	
	
    private void addProxAlert() {
    	
		Location location = ProximityAlertSetActivity.this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    	
		if (location != null) {
			
	    	Intent locationReached = new Intent();
	    	locationReached.setAction(PROX_ALERT_URI);
	    	PendingIntent proximityIntent = PendingIntent.getBroadcast(this, 0, locationReached, 0);
		         
	    	locationManager.addProximityAlert(location.getLatitude(), location.getLongitude(), PROXIMITY_RADIUS, -1, proximityIntent);
	    	Toast.makeText(ProximityAlertSetActivity.this,this.getResources().getString(R.string.saved), Toast.LENGTH_SHORT).show();
			
		} else {
			Toast.makeText(ProximityAlertSetActivity.this,this.getResources().getString(R.string.no_location), Toast.LENGTH_SHORT).show();
		}
    	
}
	
}
