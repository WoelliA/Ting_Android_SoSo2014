package de.ur.mi.android.ting.app.fragments;

import de.ur.mi.android.ting.app.activities.ILoginHandler;
import android.view.View;

public class LoginFragmentBase extends FragmentBase {
	protected ILoginHandler handler;

	public void setHandler (ILoginHandler handler){
		this.handler = handler;
		
	}


}

