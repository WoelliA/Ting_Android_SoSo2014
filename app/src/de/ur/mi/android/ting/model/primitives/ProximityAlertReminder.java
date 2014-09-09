package de.ur.mi.android.ting.model.primitives;

import android.app.PendingIntent;


public class ProximityAlertReminder implements Comparable<ProximityAlertReminder> {
	
	private String name;
	private double lat;
	private double lng;

	
	public ProximityAlertReminder(String name, double lat, double lng) {
		this.name = name;
		this.lat = lat;
		this.lng = lng;
	}
	
	public String getName() {
		return name;
	}
	
	public double getLat() {
		return lat;
	}
	
	public double getLng() {
		return lng;
	}

	
	@Override
	public int compareTo(ProximityAlertReminder proximityAlertReminder){
		int comparison_result = name.compareToIgnoreCase(proximityAlertReminder.getName());
		if (comparison_result == 0) {
			comparison_result = -1;
		}
		return comparison_result;
	}



}
