package de.ur.mi.android.ting.model.parse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.util.Log;

import com.parse.ParseFile;
import com.parse.ParseObject;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.model.primitives.User;

public class ParseHelper {

	private static ParseCache cache() {
		return ParseCache.current();
	}

	public static User createUser(ParseObject parseObject) {
		cache().put(parseObject);

		String profilePictureUrl = null;
		Object object;
		if (parseObject.has("profile_picture")) {
			profilePictureUrl = parseObject.getString("profile_picture");
			object = parseObject.get("profile_picture");
			ParseFile file = (ParseFile) object;
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
		if (parseObject.has("info")) {
			user.setInfo(parseObject.getString("info"));
		}

		return user;
	}

	public static Board createBoard(ParseObject o) {
		if (o == null) {
			return null;
		}
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
		String title = getStringIfHas(object, "title");
		String description = getStringIfHas(object, "description");
		String image = getStringIfHas(object, "image");
		String linkUrl = getStringIfHas(object, "url");
		double aspectRatio = object.has("aspectratio") ? object
				.getDouble("aspectratio") : 1;
		ParseObject board = object.has("board") ? object
				.getParseObject("board") : null;
		return new Pin(object.getObjectId(), title, description, image,
				ParseHelper.createBoard(board), linkUrl, aspectRatio);
	}

	private static String getStringIfHas(ParseObject object, String key) {
		return object.has(key) ? object.getString(key) : "";
	}

	public static Collection<User> createUsers(
			List<? extends ParseObject> objects) {
		if (objects == null) {
			return null;
		}
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

	public static List<Category> createCategories(List<ParseObject> objects) {
		ArrayList<Category> categories = new ArrayList<Category>();
		if (objects != null) {
			for (ParseObject parseObject : objects) {
				categories.add(ParseHelper.createCategory(parseObject));
			}
		}
		return categories;
	}

	public static Collection<ParseObject> toParseObjects(String classname,
			Collection<String> ids) {
		List<ParseObject> objects = new ArrayList<ParseObject>();
		if (ids != null) {
			for (String id : ids) {
				objects.add(ParseObject.createWithoutData(classname, id));
			}
		}
		return objects;
	}

}
