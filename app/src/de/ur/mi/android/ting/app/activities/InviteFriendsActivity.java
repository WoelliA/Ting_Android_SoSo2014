package de.ur.mi.android.ting.app.activities;

import javax.inject.Inject;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.controllers.FriendsController;
import de.ur.mi.android.ting.app.controllers.FriendsController.IFriendsView;
import android.os.Bundle;

public class InviteFriendsActivity extends BaseActivity implements IFriendsView {
	
	@Inject
	public FriendsController friendsController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_invite_friends);
		
		this.initUi();
	}

	private void initUi() {
		
		
	}
	
	@Override
	public boolean skipInject() {
		return true;
	}
}
