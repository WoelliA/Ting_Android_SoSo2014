package de.ur.mi.android.ting.app.controllers;

import javax.inject.Inject;

import android.content.Context;
import android.content.Intent;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.activities.ShareActivity;
import de.ur.mi.android.ting.app.activities.ShareActivity.ShareStage;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.PinAffiliation;
import de.ur.mi.android.ting.model.primitives.Pin;

public class PinController {

	private LocalUser user;
	private IUserService userService;
	private Context context;

	@Inject
	public PinController(IUserService userService, LocalUser user) {
		this.userService = userService;
		this.user = user;
	}

	public void setup(Context context) {
		this.context = context;
	}

	public void reting(Pin pin) {
		if(this.isNotLoggedIn(R.string.needs_login_reting)) {
			return;
		}
		Intent intent = new Intent(this.context, ShareActivity.class);
		intent.putExtra(ShareActivity.STAGE_KEY, ShareStage.BoardSelect);
		intent.putExtra(ShareActivity.PIN_ID_KEY, pin.getId());
		this.context.startActivity(intent);
	}

	public boolean like(Pin pin) {
		if (this.isNotLoggedIn(R.string.needs_login_like)) {
			return false;
		}

		this.user.getLikedPins().add(pin);
		pin.setAffiliation(PinAffiliation.Liked);
		this.userService.setPinLike(pin, true);
		return true;
	}

	private boolean isNotLoggedIn(int explanationResId) {
		if (!this.user.getIsLogedIn()) {
			NotifyRequiresLogin notify = new NotifyRequiresLogin(this.context,explanationResId);
			notify.show();
			return true;
		}
		return false;
	}

	public void unlike(Pin pin, Context context) {
		this.user.getLikedPins().remove(pin);
		pin.setAffiliation(PinAffiliation.None);
		this.userService.setPinLike(pin, false);
	}

	public boolean getIsLiked(Pin pin) {
		return pin.getAffiliation(this.user) == PinAffiliation.Liked;
	}

	public boolean getIsOwned(Pin pin) {
		return pin.getAffiliation(this.user) == PinAffiliation.Owned;
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
