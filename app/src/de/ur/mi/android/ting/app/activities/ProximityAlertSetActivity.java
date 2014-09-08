package de.ur.mi.android.ting.app.activities;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.db.ReminderDB;
import de.ur.mi.android.ting.model.primitives.ProximityAlertReminder;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.CalendarContract.Reminders;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.FlagToString;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProximityAlertSetActivity extends Activity{
	
	private ReminderDB database;
	private ProximityAlertReminder proximityAlertReminder;
	
	private EditText eTName;
	private EditText eTLatitude;
	private EditText eTLongitude;
	
	private static DecimalFormat nf;
	private static final long MIN_UPDATE_TIME = 1*60*1000;//min*sec*millsec
	private static final float MIN_UPDATE_DISTANCE = 5;//meter(s)     
	private LocationManager locationManager;
	private LocationListener locationListener;
	private PendingIntent pIntent;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_proximity);
		
		//setIntent();
		initNumberFormat();
		initLocationManager();
		initDB();
		initUI();
		
	}
	
	
	
	
	private void initNumberFormat() {
		nf = new DecimalFormat("##.########");		
		DecimalFormatSymbols custom = new DecimalFormatSymbols();
		custom.setDecimalSeparator('.');
		nf.setDecimalFormatSymbols(custom);
		
	}




	private void setIntent() {
		Intent intent = new Intent();
		pIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		
	}




	private void initLocationManager() {
		initLocationListener();
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_UPDATE_TIME, MIN_UPDATE_DISTANCE, locationListener);
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
				// TODO Auto-generated method stub
				
			}
		};
		
	}



	private void initDB() {
		database = new ReminderDB(this);
		database.open();	
	}




	private void initUI() {
		initEditTexts();
		initGetPositionButton();
		initAddButton();
		
	}




	private void initEditTexts() {
		eTName = (EditText)findViewById(R.id.name_reminder);
		eTLatitude = (EditText)findViewById(R.id.latitude);
		eTLongitude = (EditText)findViewById(R.id.longitude);
	}




	private void initGetPositionButton() {
		Button getPosition = (Button)findViewById(R.id.button_get_position);
		getPosition.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setEditTextsToLastKnownLocation();
				
			}
		});
	}




	protected void setEditTextsToLastKnownLocation() {
		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(location!=null){
			eTLatitude.setText(nf.format(location.getLatitude()));
			eTLongitude.setText(nf.format(location.getLongitude()));
		} else {
			Toast.makeText(ProximityAlertSetActivity.this,getResources().getString(R.string.no_location), Toast.LENGTH_SHORT).show();
		}
		
	}




	private void initAddButton() {
		Button saveReminder = (Button)findViewById(R.id.button_save_reminder);
		saveReminder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = eTName.getText().toString();
				String latitude = eTLatitude.getText().toString();
				String longitude = eTLongitude.getText().toString();
				double lat = Double.parseDouble(latitude);
				double lng = Double.parseDouble(longitude);
				if (name.equals("") || latitude.equals("") || longitude.equals("")) {
					Toast.makeText(ProximityAlertSetActivity.this,getResources().getString(R.string.fill_in), Toast.LENGTH_SHORT).show();
					
				} else {
					proximityAlertReminder = new ProximityAlertReminder(name, lat, lng);
					database.insertItemIntoDataBase(proximityAlertReminder);
					Toast.makeText(ProximityAlertSetActivity.this,proximityAlertReminder.getName() + " saved", Toast.LENGTH_SHORT).show();
				}
				
			}
		
		});
	
	}
	
}
