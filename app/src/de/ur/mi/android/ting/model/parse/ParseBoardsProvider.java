package de.ur.mi.android.ting.model.parse;

import com.parse.ParseObject;

import de.ur.mi.android.ting.model.IBoardsProvider;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.Board;

public class ParseBoardsProvider implements IBoardsProvider {

	private LocalUser user;

	public ParseBoardsProvider(LocalUser user) {
		this.user = user;
	}

	

}
