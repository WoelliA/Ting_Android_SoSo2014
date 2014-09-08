package de.ur.mi.android.ting.app.fragments;

import android.os.Bundle;
import de.ur.mi.android.ting.app.IInjector;

public class FragmentBase extends android.support.v4.app.Fragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (!skipInject())
			((IInjector) this.getActivity()).inject(this);
	}

	protected boolean skipInject() {
		return false;
	}
}
