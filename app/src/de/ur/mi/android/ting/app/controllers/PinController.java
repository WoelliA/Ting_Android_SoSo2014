package de.ur.mi.android.ting.app.controllers;

import javax.inject.Inject;

import android.content.Context;
import android.content.Intent;
import de.ur.mi.android.ting.app.activities.ShareActivity;
import de.ur.mi.android.ting.app.activities.ShareActivity.ShareStage;
import de.ur.mi.android.ting.app.viewResolvers.PinViewResolver;
import de.ur.mi.android.ting.model.IPinService;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.PinAffiliation;
import de.ur.mi.android.ting.model.primitives.Pin;

public class PinController {

	private LocalUser user;
	private IUserService userService;

	@Inject
	public PinController(IUserService userService, LocalUser user) {
		this.userService = userService;
		this.user = user;
	}

	public void reting(Pin pin, Context context) {
		Intent intent = new Intent(context, ShareActivity.class);
		intent.putExtra(ShareActivity.STAGE_KEY, ShareStage.BoardSelect);
		intent.putExtra(ShareActivity.PIN_ID_KEY, pin.getId());
		context.startActivity(intent);
	}

	public void like(Pin pin, Context context) {
		user.getLikedPins().add(pin);
		pin.setAffiliation(PinAffiliation.Liked);
		userService.setPinLike(pin, true);
	}

	public void unlike(Pin pin, Context context) {
		user.getLikedPins().remove(pin);
		pin.setAffiliation(PinAffiliation.None);
		userService.setPinLike(pin, false);
	}

	public boolean getIsLiked(Pin pin) {
		return pin.getAffiliation(user) == PinAffiliation.Liked;
	}

	public boolean getIsOwned(Pin pin) {
		return pin.getAffiliation(user) == PinAffiliation.Owned;
	}

	public void share(Pin pin, Context context) {
		String shareText = pin.getLinkUri();

		Intent textShareIntent = new Intent(Intent.ACTION_SEND);
		textShareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
		textShareIntent.setType("text/plain");
		context.startActivity(Intent.createChooser(textShareIntent,
				"Share Link with..."));

	}
}
