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
		setContentView(R.layout.activity_proximity);
		
		initDatabase();
		initReminders();
		initUI();
		
	}
	
	private void initDatabase() {
		database = new ReminderDB(this);
		database.open();
		
	}
	
	private void initReminders() {
		reminders = new ArrayList<ProximityAlertReminder>();
		getItemsFromDatabase();
	}

	private void getItemsFromDatabase() {
		reminders.clear();
		ArrayList<ProximityAlertReminder> dbItems = database.getAllItemFromDatabase();
		reminders = new ArrayList<ProximityAlertReminder>(dbItems);
		Collections.sort(reminders);
	}

	private void initUI() {
		initAddButton();
		initListView();
		initListAdapter();
	}



	private void initListView() {
		reminderList = (ListView) findViewById(R.id.reminder_list);
		reminderList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				removeReminderAtPosition(position);
				return false;
			}
		});
	}

	private void removeReminderAtPosition(int position) {
		if (reminders.get(position) == null) {
			return;
		} else {
			database.removeItemFromDataBase(reminders.get(position));
			reminders.remove(position);
			reminders_adapter.notifyDataSetChanged();
			Toast.makeText(ProximityAlertActivity.this,"deleted", Toast.LENGTH_SHORT).show();
		}
		
	}

	private void initAddButton() {
		addReminderButton = (Button) findViewById(R.id.button_add_reminder);
		addReminderButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Context context = v.getContext();
				Intent intent = new Intent(context, ProximityAlertSetActivity.class);
				context.startActivity(intent);
			}
		});
	}

	private void initListAdapter() {
		reminders_adapter = new ReminderListAdapter(this, reminders);
		reminderList.setAdapter(reminders_adapter);
		reminders_adapter.notifyDataSetChanged();	
	}     
	
}


