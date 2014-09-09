package de.ur.mi.android.ting.model.primitives;


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
		return this.name;
	}
	
	public double getLat() {
		return this.lat;
	}
	
	public double getLng() {
		return this.lng;
	}

	
	@Override
	public int compareTo(ProximityAlertReminder proximityAlertReminder){
		int comparison_result = this.name.compareToIgnoreCase(proximityAlertReminder.getName());
		if (comparison_result == 0) {
			comparison_result = -1;
		}
		return comparison_result;
	}



}
