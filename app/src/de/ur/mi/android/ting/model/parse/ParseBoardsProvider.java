package de.ur.mi.android.ting.model.parse;

import com.parse.ParseObject;

import de.ur.mi.android.ting.model.IBoardsProvider;
import de.ur.mi.android.ting.model.primitives.Board;

public class ParseBoardsProvider implements IBoardsProvider {

	private ParseCategoryProvider categoryProvider;
	private ParseUserService userService;

	public ParseBoardsProvider(ParseCategoryProvider categoryProvider,
			ParseUserService userService) {
		this.categoryProvider = categoryProvider;
		this.userService = userService;

	}

	public Board createBoard(ParseObject parseObject) {
		return new Board(parseObject.getObjectId(),
				this.categoryProvider.createCategory(parseObject),
				parseObject.getString("name"),
				parseObject.getString("description"),
				this.userService.createUser(parseObject));
	}

}
