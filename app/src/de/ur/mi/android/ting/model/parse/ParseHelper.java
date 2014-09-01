package de.ur.mi.android.ting.model.parse;

import com.parse.ParseObject;

import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.User;

public class ParseHelper {

	public static User createUser(ParseObject parseObject) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Board createBoard(ParseObject parseObject) {
		return new Board(parseObject.getObjectId(),
				ParseHelper.createCategory(parseObject),
				parseObject.getString("name"),
				parseObject.getString("description"),
				ParseHelper.createUser(parseObject));
	}
	

	protected static Category createCategory(ParseObject parseObject) {
		Category category = new Category(parseObject.getObjectId(),
				parseObject.getString("category_name"),
				parseObject.getString("short"));
		return category;
	}

}
