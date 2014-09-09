package de.ur.mi.android.ting.app.adapters;

import java.util.ArrayList;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.model.primitives.ProximityAlertReminder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ReminderListAdapter extends ArrayAdapter<ProximityAlertReminder>{

	private ArrayList<ProximityAlertReminder> proxList;
	private Context context;
	
	public ReminderListAdapter(Context context, ArrayList<ProximityAlertReminder> listItems) {
		super(context, R.layout.reminder_layout, listItems);
		
		this.context = context;
		this.proxList = listItems;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		
		if (v==null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.reminder_layout, null);
		}
		
		ProximityAlertReminder proxRem = proxList.get(position);
		
		if (proxRem != null) {
			TextView reminderName = (TextView) v.findViewById(R.id.reminder_name);
			
			reminderName.setText(proxRem.getName());
		}
		
		return v;
	}
	
}
