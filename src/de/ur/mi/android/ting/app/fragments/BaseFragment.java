package de.ur.mi.android.ting.app.fragments;

import de.ur.mi.android.ting.app.IInjector;
import android.app.Fragment;
import android.os.Bundle;

public class BaseFragment extends Fragment{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		((IInjector)this.getActivity()).inject(this);
		super.onCreate(savedInstanceState);
	}
}
