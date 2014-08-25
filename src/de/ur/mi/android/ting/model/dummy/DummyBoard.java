package de.ur.mi.android.ting.model.dummy;

import de.ur.mi.android.ting.model.Primitives.Board;

public class DummyBoard extends Board {

	public DummyBoard(int i) {
		super("board id " + i, new DummyCategory(i), "Board Title Swag " + i,
				"Lorem ipsum swagum a lotso fotso gonzo hipsterum hitleri goi",
				new DummyUser(i));
	}

}
