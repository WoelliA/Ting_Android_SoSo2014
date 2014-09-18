package de.ur.mi.android.ting.model.parse;

import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import de.ur.mi.android.ting.utilities.cache.SoftRefMemoryCache;

public class ParseCache extends SoftRefMemoryCache<String, ParseObject>{
	
	private static final String LOG_TAG = ParseCache.class.getName();
	private static ParseCache current;

	public static ParseCache current(){
		if(current == null) {
			current = new ParseCache();
		}
		return current;
	}
	
	public boolean put(ParseObject object){
		String id = object.getObjectId();
		ParseObject olderObject = this.get(id);
		if(olderObject == null || !olderObject.isDataAvailable()){
			return this.put(object.getObjectId(), object);
		}

		Log.i(LOG_TAG, "data is already available");
		return false;
	}

	public boolean restore(String id, final GetCallback<ParseObject> callback) {
		ParseObject parseObject = ParseCache.current().get(id);
		if(parseObject != null){
			parseObject.fetchIfNeededInBackground(new GetCallback<ParseObject>() {

				@Override
				public void done(ParseObject object, ParseException e) {
					callback.done(object, e);					
				}
			});
			return true;
		}
		return false;
	}
}
