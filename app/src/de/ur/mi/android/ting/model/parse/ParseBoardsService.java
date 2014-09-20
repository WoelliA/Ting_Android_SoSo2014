package de.ur.mi.android.ting.model.parse;

import java.io.NotActiveException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.parse.ParseQuery.CachePolicy;
import com.parse.ParseUser;

import de.ur.mi.android.ting.app.controllers.BoardEditRequest;
import de.ur.mi.android.ting.app.controllers.CategoryBoardsRequest;
import de.ur.mi.android.ting.model.BoardSorting;
import de.ur.mi.android.ting.model.IBoardsService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.PagingRequestBase;
import de.ur.mi.android.ting.model.primitives.SearchRequest;
import de.ur.mi.android.ting.model.primitives.SearchResult;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public class ParseBoardsService implements IBoardsService {

	private LocalUser localuser;

	private static final String BOARD_CLASS_NAME = "board";
	private static final String BOARD_TITLE_KEY = "name";
	private static final String BOARD_DESCRIPTION_KEY = "description";
	private static final String BOARD_CATEGORY_KEY = "category";
	private static final String BOARD_OWNER_KEY = "owner";

	private String[] searchableFields = new String[] { "name", "description" };

	protected ParseObject boardRef;

	public ParseBoardsService(LocalUser user) {
		this.localuser = user;
	}

	@Override
	public void getUserBoards(final String id,
			final IDoneCallback<Collection<Board>> callback) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(BOARD_CLASS_NAME);

		if (id.equals(this.localuser.getId())) {
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
					Collection<Board> boards = ParseHelper
							.createBoards(objects);
					callback.done(boards);
				}
			}
		});
	}

	@Override
	public void getLocalUserBoards(IDoneCallback<Collection<Board>> callback) {
		this.getUserBoards(this.localuser.getId(), callback);
	}

	@Override
	public void getBoard(String boardId, final IDoneCallback<Board> callback) {
		if (!ParseCache.current().restore(boardId,
				new GetCallback<ParseObject>() {

					@Override
					public void done(ParseObject object, ParseException e) {
						ParseBoardsService.this.boardRef = object;
						callback.done(ParseHelper.createBoard(object));
					}
				}))

		{
			ParseQuery<ParseObject> query = ParseQuery
					.getQuery(BOARD_CLASS_NAME);
			query.getInBackground(boardId, new GetCallback<ParseObject>() {

				@Override
				public void done(ParseObject object, ParseException e) {
					ParseBoardsService.this.boardRef = object;
					callback.done(ParseHelper.createBoard(object));
				}
			});
		}
	}

	@Override
	public <T> void search(final SearchRequest request,
			final IDoneCallback<SearchResult<T>> callback) {
		ParseQuery<ParseObject> query = ParseQueryHelper.getSearchQuery(
				BOARD_CLASS_NAME, request, this.searchableFields);

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
			final IDoneCallback<Board> callback) {
		final ParseObject board = ParseObject.create(BOARD_CLASS_NAME);
		board.put(BOARD_TITLE_KEY, request.getTitle());
		board.put(BOARD_DESCRIPTION_KEY, request.getDescription());

		board.put(
				BOARD_CATEGORY_KEY,
				ParseObject.createWithoutData("category",
						request.getCategoryId()));

		board.put(BOARD_OWNER_KEY, ParseUser.getCurrentUser());

		board.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				if (e == null) {
					callback.done(ParseHelper.createBoard(board));
				} else {
					callback.fail(e);
				}
			}
		});
	}

	@Override
	public void saveBoard(BoardEditRequest request,
			final IDoneCallback<Board> callback) {
		this.boardRef.put(BOARD_TITLE_KEY, request.getTitle());
		this.boardRef.put(BOARD_DESCRIPTION_KEY, request.getDescription());

		this.boardRef.put(
				BOARD_CATEGORY_KEY,
				ParseObject.createWithoutData("category",
						request.getCategoryId()));

		this.boardRef.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				if (e == null) {
					callback.done(ParseHelper
							.createBoard(ParseBoardsService.this.boardRef));
				} else {
					callback.fail(e);
				}
			}
		});
	}

	@Override
	public void getBoardsForCategories(CategoryBoardsRequest request,
			final IDoneCallback<Collection<Board>> callback) {
		HashMap<String, Object> params = new HashMap<String, Object>();

		String functionName = null;
		BoardSorting sorting = request.getSorting();
		switch (sorting) {
		case Popular:
			functionName = "getPopularBoardsForCategories";
			break;
		default:
			Log.e(this.getClass().getName(), "BoardSorting not implemented: "
					+ sorting);
			return;
		}

		params.put("categoryIds", request.getCategoryIds());
		params.put("from", "" + request.getOffset());
		params.put("count", "" + request.getCount());

		ParseCloud.callFunctionInBackground(functionName, params,
				new FunctionCallback<List<ParseObject>>() {
					@Override
					public void done(List<ParseObject> objects, ParseException e) {
						callback.done(ParseHelper.createBoards(objects));
					}
				});

	}
}
