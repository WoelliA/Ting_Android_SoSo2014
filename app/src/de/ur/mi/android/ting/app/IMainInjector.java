package de.ur.mi.android.ting.app;

import dagger.ObjectGraph;

public interface IMainInjector extends IInjector{

	IInjector plus(Object... params);
}
