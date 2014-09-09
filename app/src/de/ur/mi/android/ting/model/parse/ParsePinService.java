package de.ur.mi.android.ting.model.parse;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQuery.CachePolicy;

import de.ur.mi.android.ting.model.IPinService;
import de.ur.mi.android.ting.model.IPinReceivedCallback;
import de.ur.mi.android.ting.model.PinData;
import de.ur.mi.android.ting.model.PinRequest;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public class ParsePinService implements IPinService {

	@Override
	public void getPinsForCategory(Category category, PinRequest request,
			final IPinReceivedCallback callback) {
		
		
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("pin");
		
		query.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
		query.setMaxCacheAge(60000);
		
		query.setLimit(request.getCount());
		query.setSkip(request.getOffset());

		query.include("board");

		ParseObject parseCategory = new ParseObject("category");
		parseCategory.setObjectId(category.getId());
		query.whereEqualTo("category", parseCategory);

		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					callback.onPinsReceived(ParsePinService.this.createPins(objects));
				} else {
					Log.e("parse query pin", e.getMessage());
				}
			}
		});

	}

	protected ArrayList<Pin> createPins(List<ParseObject> objects) {
		ArrayList<Pin> pins = new ArrayList<Pin>();
		for (ParseObject object : objects) {
			pins.add(ParseHelper.createPin(object));
		}
		return pins;
	}


	@Override
	public void createPin(PinData result, Board selectedBoard, IDoneCallback callback) {
		// TODO implement parse create pin
		
	}

	@Override
	public void getPinsForBoard(String boardId, PinRequest request,
			IDoneCallback<List<Pin>> simpleDoneCallback) {
		// TODO implement parse call for pins filtered by board
		
	}

}
