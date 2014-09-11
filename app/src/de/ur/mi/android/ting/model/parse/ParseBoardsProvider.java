package de.ur.mi.android.ting.model.parse;

import java.util.Collection;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQuery.CachePolicy;
import com.parse.ParseUser;

import de.ur.mi.android.ting.model.IBoardsService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.SearchRequest;
import de.ur.mi.android.ting.model.primitives.SearchResult;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public class ParseBoardsProvider implements IBoardsService {

	private LocalUser user;
	
	private static final String BOARD_CLASS_NAME = "board";
	private String[] searchableFields = new String[] { "name", "description" };

	public ParseBoardsProvider(LocalUser user) {
		this.user = user;
	}

	@Override
	public void getUserBoards(String id,
			final IDoneCallback<Collection<Board>> callback) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(BOARD_CLASS_NAME);
		
		if(id.equals(user.getId())){
			query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
			query.setMaxCacheAge(60000);
		}
		
		ParseUser user = new ParseUser();
		user.setObjectId(id);	
		
		
		query.whereEqualTo("owner",user);
		
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if(e == null && objects != null){
					callback.done(ParseHelper.createBoards(objects));
				}
			}
		});
	}

	@Override
	public void getLocalUserBoards(IDoneCallback<Collection<Board>> callback) {
		this.getUserBoards(this.user.getId(), callback);
	}

	@Override
	public void getBoard(String boardId, final IDoneCallback<Board> callback) {
		if (!ParseCache.current().restore(boardId,
				new GetCallback<ParseObject>() {

					@Override
					public void done(ParseObject object, ParseException e) {
						callback.done(ParseHelper.createBoard(object));
					}
				}))

		{
			ParseQuery<ParseObject> query = ParseQuery.getQuery(BOARD_CLASS_NAME);
			query.getInBackground(boardId, new GetCallback<ParseObject>() {

				@Override
				public void done(ParseObject object, ParseException e) {
					callback.done(ParseHelper.createBoard(object));
				}
			});
		}
	}

	@Override
	public <T> void search(final SearchRequest request,
			final IDoneCallback<SearchResult<T>> callback) {
		ParseQuery<ParseObject> query = ParseQueryHelper.getSearchQuery(
				BOARD_CLASS_NAME, request, searchableFields);

		query.findInBackground(new FindCallback<ParseObject>() {

			@SuppressWarnings("unchecked")
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null && objects != null) {
					callback.done((SearchResult<T>) new SearchResult<Board>(
							ParseHelper.createBoards(objects), request
									.getCount()));
				}
			}
		});

	}


}
