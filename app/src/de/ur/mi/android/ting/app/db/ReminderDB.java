package de.ur.mi.android.ting.app.db;

import java.util.ArrayList;

import de.ur.mi.android.ting.model.primitives.ProximityAlertReminder;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class ReminderDB {
	
	private ReminderDBHelper helper;
	private SQLiteDatabase db;
	public static final String DB_NAME = "proximity-alerts";
	public static final int DB_VERSION = 1;
	public static final String DATABASE_TABLE = "reminders";
	public static final String KEY_NAME = "_name";
	public static final String KEY_LATITUDE = "_latitude";
	public static final String KEY_LONGITUDE = "_longitude";

	public ReminderDB(Context context){
		this.helper = new ReminderDBHelper(context, DB_NAME, null, DB_VERSION);
	}
	
	public void open(){
		this.db = this.helper.getWritableDatabase();
	}
	
	public void close(){
		this.db.close();
		this.helper.close();
	}
	
	public void insertItemIntoDataBase(ProximityAlertReminder item) {
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, item.getName());
		values.put(KEY_LATITUDE, item.getLat());
		values.put(KEY_LONGITUDE, item.getLng());
		this.db.insert(DATABASE_TABLE, null, values);
	}
	
	public void removeItemFromDataBase(ProximityAlertReminder item) {
		String delete_clause = KEY_NAME+"=?";
		String[] delete_args = new String[] {item.getName()};
		this.db.delete(DATABASE_TABLE, delete_clause, delete_args);
	}
	
	public void removeAllItemsFromDatabase() {
		this.db.delete(DATABASE_TABLE, null, null);
	}
	
	public ArrayList<ProximityAlertReminder> getAllItemFromDatabase() {
		ArrayList<ProximityAlertReminder> items = new ArrayList<ProximityAlertReminder>();
		Cursor results = this.db.query(DATABASE_TABLE, new String[]{KEY_NAME,KEY_LATITUDE,KEY_LONGITUDE}, null, null, null, null, null);
		if(results.moveToFirst()) {
			do {
				String name = results.getString(0);
				double lat = results.getDouble(1);
				double lng = results.getDouble(2);
				items.add(new ProximityAlertReminder(name, lat, lng));
				
			} while(results.moveToNext());
		}
		return items;
	}
	
	
	private class ReminderDBHelper extends SQLiteOpenHelper {
		
		private static final String DATABASE_CREATE = "create table "
				+ DATABASE_TABLE + " (" + KEY_NAME
				+ " String primary key, "
				+ KEY_LATITUDE + " double not null, " + KEY_LONGITUDE + " double not null);" ;

		
		public ReminderDBHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

	}
}
