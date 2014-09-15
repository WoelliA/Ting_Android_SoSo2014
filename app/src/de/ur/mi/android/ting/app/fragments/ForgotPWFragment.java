package de.ur.mi.android.ting.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.ur.mi.android.ting.R;

public class ForgotPWFragment extends LoginFragmentBase {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_passwordrestore, container, false);
	}
}
