package de.ur.mi.android.ting.model.parse;

import java.util.Collection;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.parse.ParseQuery.CachePolicy;
import com.parse.ParseUser;

import de.ur.mi.android.ting.app.controllers.BoardEditRequest;
import de.ur.mi.android.ting.model.IBoardsService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.parse.callbacks.SaveCallbackWrap;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.SearchRequest;
import de.ur.mi.android.ting.model.primitives.SearchResult;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public class ParseBoardsProvider implements IBoardsService {

	private LocalUser user;

	private static final String BOARD_CLASS_NAME = "board";
	private static final String BOARD_TITLE_KEY = "name";
	private static final String BOARD_DESCRIPTION_KEY = "description";
	private static final String BOARD_CATEGORY_KEY = "category";
	private static final String BOARD_OWNER_KEY = "owner";

	private String[] searchableFields = new String[] { "name", "description" };

	protected ParseObject boardRef;

	public ParseBoardsProvider(LocalUser user) {
		this.user = user;
	}

	@Override
	public void getUserBoards(String id,
			final IDoneCallback<Collection<Board>> callback) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(BOARD_CLASS_NAME);

		if (id.equals(user.getId())) {
			query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
			query.setMaxCacheAge(60000);
		}

		ParseUser user = new ParseUser();
		user.setObjectId(id);

		query.whereEqualTo("owner", user);

		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null && objects != null) {
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
						ParseBoardsProvider.this.boardRef = object;
						callback.done(ParseHelper.createBoard(object));
					}
				}))

		{
			ParseQuery<ParseObject> query = ParseQuery
					.getQuery(BOARD_CLASS_NAME);
			query.getInBackground(boardId, new GetCallback<ParseObject>() {

				@Override
				public void done(ParseObject object, ParseException e) {
					ParseBoardsProvider.this.boardRef = object;
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

	@Override
	public void createBoard(BoardEditRequest request,
			final IDoneCallback<Void> callback) {
		ParseObject board = ParseObject.create(BOARD_CLASS_NAME);
		board.put(BOARD_TITLE_KEY, request.getTitle());
		board.put(BOARD_DESCRIPTION_KEY, request.getDescription());

		board.put(
				BOARD_CATEGORY_KEY,
				ParseObject.createWithoutData("category",
						request.getCategoryId()));

		board.put(BOARD_OWNER_KEY, ParseUser.getCurrentUser());

		board.saveInBackground(new SaveCallbackWrap(callback));
	}

	@Override
	public void saveBoard(BoardEditRequest request, IDoneCallback<Void> callback) {
		boardRef.put(BOARD_TITLE_KEY, request.getTitle());
		boardRef.put(BOARD_DESCRIPTION_KEY, request.getDescription());

		boardRef.add(
				BOARD_CATEGORY_KEY,
				ParseObject.createWithoutData("category",
						request.getCategoryId()));

		boardRef.saveInBackground(new SaveCallbackWrap(callback));
	}
}
