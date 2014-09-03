package de.ur.mi.android.ting.model.parse;

import de.ur.mi.android.ting.model.IBoardsProvider;
import de.ur.mi.android.ting.model.LocalUser;

public class ParseBoardsProvider implements IBoardsProvider {

	private LocalUser user;

	public ParseBoardsProvider(LocalUser user) {
		this.user = user;
	}

	

}
