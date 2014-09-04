package de.ur.mi.android.ting.model.dummy;

import java.util.ArrayList;

import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.User;

public class DummyBoard extends Board {

	public DummyBoard(int i) {
		super("board id " + i, new DummyCategory(i), "Board Title Swag " + i,
				"Lorem ipsum swagum a lotso fotso gonzo hipsterum hitleri goi",
				new DummyUser(i), new ArrayList<User>());
		for (int j = 0; j < 10; j++) {
			this.contributors.add(new DummyUser(j));
		}
	}

}
