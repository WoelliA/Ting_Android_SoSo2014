package de.ur.mi.android.ting.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import de.ur.mi.android.ting.app.IInjector;

public class FragmentBase extends android.support.v4.app.Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		((IInjector)this.getActivity()).inject(this);
		super.onCreate(savedInstanceState);
	}
}
