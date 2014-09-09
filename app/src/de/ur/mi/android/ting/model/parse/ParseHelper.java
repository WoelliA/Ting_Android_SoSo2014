package de.ur.mi.android.ting.model.parse;

import com.parse.ParseObject;

import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.Pin;
import de.ur.mi.android.ting.model.primitives.User;

public class ParseHelper {

	public static User createUser(ParseObject parseObject) {
		String profilePictureUrl = parseObject.getString("profile_picture");
		if (profilePictureUrl == null || profilePictureUrl == "") {
			profilePictureUrl = "file:///android_asset/defaultprofile.png";
		}
		return new User(parseObject.getObjectId(),
				parseObject.getString("name"), profilePictureUrl);
	}

	public static Board createBoard(ParseObject parseObject) {
		return new Board(parseObject.getObjectId(),
				ParseHelper.createCategory(parseObject),
				parseObject.getString("name"),
				parseObject.getString("description"),
				ParseHelper.createUser(parseObject), null);
	}

	protected static Category createCategory(ParseObject parseObject) {
		Category category = new Category(parseObject.getObjectId(),
				parseObject.getString("category_name"),
				parseObject.getString("short"));
		return category;
	}

	public static Pin createPin(ParseObject object) {
		return new Pin(object.getObjectId(), object.getString("title"),
				object.getString("description"), object.getString("image"),
				ParseHelper.createBoard(object.getParseObject("board")),
				object.getString("url"), object.getDouble("aspectratio"));
	}

}
