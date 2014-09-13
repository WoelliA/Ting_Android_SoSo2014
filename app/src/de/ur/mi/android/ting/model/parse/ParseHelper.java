package de.ur.mi.android.ting.model.parse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.util.Log;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.PagingRequestBase;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.model.primitives.SearchRequest;
import de.ur.mi.android.ting.model.primitives.User;
import de.ur.mi.android.ting.utilities.cache.WeakRefMemoryCache;

public class ParseHelper {

	private static ParseCache cache(){
		return ParseCache.current();
	}
	
	public static User createUser(ParseObject parseObject) {
		cache().put(parseObject);
		
		String profilePictureUrl = null;Object object;
		if (parseObject.has("profile_picture")) {
			profilePictureUrl = parseObject.getString("profile_picture");
			object = parseObject.get("profile_picture");
			ParseFile file = (ParseFile)object;
			String url = file.getUrl();
			profilePictureUrl = url;
		}
		if (profilePictureUrl == null || profilePictureUrl == "") {
			profilePictureUrl = "assets://images/defaultprofile.png";
		}
		User user = new User(parseObject.getObjectId());
		user.setProfilePictureUrl(profilePictureUrl);
		if (parseObject.has("username")) {
			user.setName(parseObject.getString("username"));
		}
		if(parseObject.has("info")){
			user.setInfo(parseObject.getString("info"));
		}

		return user;
	}

	public static Board createBoard(ParseObject o) {
		cache().put(o);
		
		Board board = new Board(o.getObjectId());
		if (o.has("category")) {
			board.setCategory(ParseHelper.createCategory(o
					.getParseObject("category")));
		}
		if (o.has("name")) {
			board.setName(o.getString("name"));
		}
		if (o.has("description")) {
			board.setDescription(o.getString("description"));
		}
		if (o.has("owner")) {
			board.setOwner(ParseHelper.createUser(o.getParseObject("owner")));
		}
		return board;
	}

	protected static Category createCategory(ParseObject parseObject) {
		
		Category category = new Category(parseObject.getObjectId());
		Log.i("parse", "category " + parseObject);
		if (parseObject.has("category_name")) {
			category.setName(parseObject.getString("category_name"));
		}
		if (parseObject.has("short")) {
			category.setShortName(parseObject.getString("short"));
		}
		return category;
	}

	public static Pin createPin(ParseObject object) {
		cache().put(object);
		
		return new Pin(object.getObjectId(), object.getString("title"),
				object.getString("description"), object.getString("image"),
				ParseHelper.createBoard(object.getParseObject("board")),
				object.getString("url"), object.getDouble("aspectratio"));
	}

	public static Collection<User> createUsers(
			List<? extends ParseObject> objects) {
		if (objects == null)
			return null;
		ArrayList<User> users = new ArrayList<User>();
		for (ParseObject parseObject : objects) {
			User user = createUser(parseObject);
			users.add(user);
		}
		return users;
	}

	public static Collection<Pin> createPins(List<ParseObject> objects) {
		ArrayList<Pin> pins = new ArrayList<Pin>();
		for (ParseObject object : objects) {
			pins.add(ParseHelper.createPin(object));
		}
		return pins;
	}

	public static Collection<Board> createBoards(List<ParseObject> objects) {
		ArrayList<Board> boards = new ArrayList<Board>();
		for (ParseObject object : objects) {
			boards.add(createBoard(object));
		}
		return boards;
	}

}
