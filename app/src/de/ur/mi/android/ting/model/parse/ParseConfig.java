package de.ur.mi.android.ting.model.parse;

import javax.inject.Inject;

public class ParseConfig {

	@Inject
	public ParseConfig() {
	}

	public String getWebsiteUrl() {
		return "http://ting.parseapp.com";
	}

}
