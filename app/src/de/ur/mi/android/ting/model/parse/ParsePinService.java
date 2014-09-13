package de.ur.mi.android.ting.model.parse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQuery.CachePolicy;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.model.IPinService;
import de.ur.mi.android.ting.model.IPinReceivedCallback;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.PinData;
import de.ur.mi.android.ting.model.PinRequest;
import de.ur.mi.android.ting.model.SpecialCategories.SpecialCategory;
import de.ur.mi.android.ting.model.parse.callbacks.SaveCallbackWrap;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.model.primitives.SearchRequest;
import de.ur.mi.android.ting.model.primitives.SearchResult;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public class ParsePinService implements IPinService {

	private LocalUser user;

	public ParsePinService(LocalUser user) {
		this.user = user;
	}

	@Override
	public void getPinsForCategory(Category category, PinRequest request,
			final IPinReceivedCallback callback) {
		if (category instanceof SpecialCategory) {
			this.getSpecialPins((SpecialCategory) category, request, callback);
		}

		ParseQuery<ParseObject> query = this.getBasePinQuery(request);

		ParseObject parseCategory = new ParseObject("category");
		parseCategory.setObjectId(category.getId());
		query.whereEqualTo("category", parseCategory);

		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					callback.onPinsReceived(ParseHelper.createPins(objects));
				} else {
					Log.e("parse query pin", e.getMessage());
				}
			}
		});

	}

	private ParseQuery<ParseObject> getBasePinQuery(PinRequest request) {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("pin");

		query.orderByDescending("createdAt");
		query.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
		query.setMaxCacheAge(60000);

		query.setLimit(request.getCount());
		query.setSkip(request.getOffset());

		query.include("board");
		return query;
	}

	private void getSpecialPins(SpecialCategory category, PinRequest request,
			IPinReceivedCallback callback) {
		switch (category.getSpecialId()) {
		case R.string.special_category_everything_name:
			this.getAllPins(request, callback);
			break;
		case R.string.special_category_feed_name:
			this.getFeed(request, callback);
		}
	}

	private void getFeed(PinRequest request, final IPinReceivedCallback callback) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("user", this.user.getId());
		params.put("from", "" + request.getOffset());
		params.put("count", "" + request.getCount());

		ParseCloud.callFunctionInBackground("getFeed", params,
				new FunctionCallback<List<ParseObject>>() {

					@Override
					public void done(List<ParseObject> objects, ParseException e) {
						if (e == null) {
							callback.onPinsReceived(ParseHelper
									.createPins(objects));
						} else {
							Log.e("parse query pin", e.getMessage());
						}
					}
				});
	}

	private void getAllPins(PinRequest request,
			final IPinReceivedCallback callback) {
		ParseQuery<ParseObject> query = this.getBasePinQuery(request);
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					callback.onPinsReceived(ParseHelper.createPins(objects));
				} else {
					Log.e("parse query pin", e.getMessage());
				}
			}
		});

	}

	@Override
	public void createPin(PinData result, Board selectedBoard,
			IDoneCallback<Void> callback) {
		ParseObject pin = ParseObject.create("pin");
		pin.put("title", result.getTitle());
		pin.put("description", result.getDescription());
		
		pin.put("board", ParseObject.createWithoutData("board", selectedBoard.getId()));

		pin.saveInBackground(new SaveCallbackWrap(callback));
	}

	@Override
	public <T> void search(final SearchRequest request,
			final IDoneCallback<SearchResult<T>> callback) {
		String[] searchedFields = new String[] { "title", "description" };
		ParseQuery<ParseObject> query = ParseQueryHelper.getSearchQuery("pin",
				request, searchedFields);

		query.orderByDescending("createdAt");
		query.findInBackground(new FindCallback<ParseObject>() {

			@SuppressWarnings("unchecked")
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null && objects != null) {
					callback.done((SearchResult<T>) new SearchResult<Pin>(
							ParseHelper.createPins(objects), request.getCount()));
				}
			}
		});
	}

	@Override
	public void getPinsForBoard(String boardId, PinRequest request,
			final IDoneCallback<Collection<Pin>> callback) {
		ParseQuery<ParseObject> query = this.getBasePinQuery(request);

		ParseObject board = ParseObject.create("board");
		board.setObjectId(boardId);
		query.whereEqualTo("board", board);

		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null && objects != null) {
					callback.done(ParseHelper.createPins(objects));
				}
			}
		});

	}

}
