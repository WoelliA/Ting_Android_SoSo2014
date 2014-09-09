package de.ur.mi.android.ting.app.activities;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.db.ReminderDB;
import de.ur.mi.android.ting.model.primitives.ProximityAlertReminder;
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
		this.setContentView(R.layout.activity_set_proximity);
		
		//setIntent();
		this.initNumberFormat();
		this.initLocationManager();
		this.initDB();
		this.initUI();
		
	}
	
	
	
	
	private void initNumberFormat() {
		nf = new DecimalFormat("##.########");		
		DecimalFormatSymbols custom = new DecimalFormatSymbols();
		custom.setDecimalSeparator('.');
		nf.setDecimalFormatSymbols(custom);
		
	}




	private void setIntent() {
		Intent intent = new Intent();
		this.pIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		
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
				// TODO Auto-generated method stub
				
			}
		};
		
	}



	private void initDB() {
		this.database = new ReminderDB(this);
		this.database.open();	
	}




	private void initUI() {
		this.initEditTexts();
		this.initGetPositionButton();
		this.initAddButton();
		
	}




	private void initEditTexts() {
		this.eTName = (EditText)this.findViewById(R.id.name_reminder);
		this.eTLatitude = (EditText)this.findViewById(R.id.latitude);
		this.eTLongitude = (EditText)this.findViewById(R.id.longitude);
	}




	private void initGetPositionButton() {
		Button getPosition = (Button)this.findViewById(R.id.button_get_position);
		getPosition.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ProximityAlertSetActivity.this.setEditTextsToLastKnownLocation();
				
			}
		});
	}




	protected void setEditTextsToLastKnownLocation() {
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
				String name = ProximityAlertSetActivity.this.eTName.getText().toString();
				String latitude = ProximityAlertSetActivity.this.eTLatitude.getText().toString();
				String longitude = ProximityAlertSetActivity.this.eTLongitude.getText().toString();
				double lat = Double.parseDouble(latitude);
				double lng = Double.parseDouble(longitude);
				if (name.equals("") || latitude.equals("") || longitude.equals("")) {
					Toast.makeText(ProximityAlertSetActivity.this,ProximityAlertSetActivity.this.getResources().getString(R.string.fill_in), Toast.LENGTH_SHORT).show();
					
				} else {
					ProximityAlertSetActivity.this.proximityAlertReminder = new ProximityAlertReminder(name, lat, lng);
					ProximityAlertSetActivity.this.database.insertItemIntoDataBase(ProximityAlertSetActivity.this.proximityAlertReminder);
					Toast.makeText(ProximityAlertSetActivity.this,ProximityAlertSetActivity.this.proximityAlertReminder.getName() + " saved", Toast.LENGTH_SHORT).show();
				}
				
			}
		
		});
	
	}
	
}
