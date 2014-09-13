package de.ur.mi.android.ting.model.dummy;

import java.util.Random;

import de.ur.mi.android.ting.model.primitives.User;

public class DummyUser extends User {

	public DummyUser(int id) {
		super("" + id, "Dummy User " + id, DummyConfig.MEDIUM_LONG_TEXT,
				getDummyProfilePicture(id));
	}

	private static Random random = new Random();

	private static String getDummyProfilePicture(int id) {
		String gender = getRandomGender();
		return String.format("http://api.randomuser.me/portraits/%s/%s.jpg",
				gender, id);
	}

	private static String getRandomGender() {
		int num = random.nextInt();
		return num % 2 == 0 ? "men" : "women";
	}

}
