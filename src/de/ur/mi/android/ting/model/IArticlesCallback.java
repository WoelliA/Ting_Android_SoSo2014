package de.ur.mi.android.ting.model;

import java.util.ArrayList;

import de.ur.mi.android.ting.model.Primitives.Pin;

public interface IArticlesCallback {
	public void onArticlesReceived(ArrayList<Pin> articles);
}
