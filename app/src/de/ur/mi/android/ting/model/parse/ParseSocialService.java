package de.ur.mi.android.ting.model.parse;

import java.util.Collection;

import javax.inject.Inject;

import android.app.Activity;

import com.facebook.Request;
import com.facebook.Session;
import com.facebook.widget.FacebookDialog;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.twitter.Twitter;

import de.ur.mi.android.ting.app.TingApp;
import de.ur.mi.android.ting.model.ISocialService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.User;
import de.ur.mi.android.ting.utilities.IDoneCallback;

public class ParseSocialService implements ISocialService {

	@Inject
	public LocalUser user;

	@Inject
	public ParseConfig config;

	public ParseSocialService() {
		TingApp.current().inject(this);
	}

	@Override
	public void getThirdPartyFriends(IDoneCallback<Collection<User>> callback) {
		Session fbSession = ParseFacebookUtils.getSession();
		Twitter twitter = ParseTwitterUtils.getTwitter();

		int requestCount = 0;
		requestCount += fbSession == null ? 0 : 1;
		requestCount += twitter == null ? 0 : 1;

		if (fbSession != null) {

		}
	}

}
