package de.ur.mi.android.ting.app.activities;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.adapters.ReminderListAdapter;
import de.ur.mi.android.ting.app.db.ReminderDB;
import de.ur.mi.android.ting.model.primitives.ProximityAlertReminder;


public class ProximityAlertActivity extends Activity{
	
	private ArrayList<ProximityAlertReminder> reminders;
	private ArrayAdapter<ProximityAlertReminder> reminders_adapter;
	private ReminderDB database;
	
	Button addReminderButton;
	ListView reminderList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_proximity);
		
		this.initDatabase();
		this.initReminders();
		this.initUI();
		
	}
	
	private void initDatabase() {
		this.database = new ReminderDB(this);
		this.database.open();
		
	}
	
	private void initReminders() {
		this.reminders = new ArrayList<ProximityAlertReminder>();
		this.getItemsFromDatabase();
	}

	private void getItemsFromDatabase() {
		this.reminders.clear();
		ArrayList<ProximityAlertReminder> dbItems = this.database.getAllItemFromDatabase();
		this.reminders = new ArrayList<ProximityAlertReminder>(dbItems);
		Collections.sort(this.reminders);
	}

	private void initUI() {
		this.initAddButton();
		this.initListView();
		this.initListAdapter();
	}



	private void initListView() {
		this.reminderList = (ListView) this.findViewById(R.id.reminder_list);
		this.reminderList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				ProximityAlertActivity.this.removeReminderAtPosition(position);
				return false;
			}
		});
	}

	private void removeReminderAtPosition(int position) {
		if (this.reminders.get(position) == null) {
			return;
		} else {
			this.database.removeItemFromDataBase(this.reminders.get(position));
			this.reminders.remove(position);
			this.reminders_adapter.notifyDataSetChanged();
			Toast.makeText(ProximityAlertActivity.this,"deleted", Toast.LENGTH_SHORT).show();
		}
		
	}

	private void initAddButton() {
		this.addReminderButton = (Button) this.findViewById(R.id.button_add_reminder);
		this.addReminderButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Context context = v.getContext();
				Intent intent = new Intent(context, ProximityAlertSetActivity.class);
				context.startActivity(intent);
			}
		});
	}

	private void initListAdapter() {
		this.reminders_adapter = new ReminderListAdapter(this, this.reminders);
		this.reminderList.setAdapter(this.reminders_adapter);
		this.reminders_adapter.notifyDataSetChanged();	
	}     
	
}


