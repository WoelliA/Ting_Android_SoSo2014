package de.ur.mi.android.ting.model.parse;

import android.util.Log;

import com.parse.ParseObject;

import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.model.primitives.User;

public class ParseHelper {

	public static User createUser(ParseObject parseObject) {
		String profilePictureUrl = null;
		if (parseObject.has("profile_picture")) {
			profilePictureUrl = parseObject.getString("profile_picture");

		}
		if (profilePictureUrl == null || profilePictureUrl == "") {
			profilePictureUrl = "file:///android_asset/defaultprofile.png";
		}
		User user = new User(parseObject.getObjectId());
		user.setProfilePictureUrl(profilePictureUrl);
		if (parseObject.has("name")) {
			user.setName(parseObject.getString("name"));
		}
		return user;
	}

	public static Board createBoard(ParseObject o) {
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
		if(parseObject.has("category_name")){
			category.setName(parseObject.getString("category_name"));
		}
		if(parseObject.has("short")){
			category.setShortName(parseObject.getString("short"));
		}
		return category;
	}

	public static Pin createPin(ParseObject object) {
		return new Pin(object.getObjectId(), object.getString("title"),
				object.getString("description"), object.getString("image"),
				ParseHelper.createBoard(object.getParseObject("board")),
				object.getString("url"), object.getDouble("aspectratio"));
	}

}
