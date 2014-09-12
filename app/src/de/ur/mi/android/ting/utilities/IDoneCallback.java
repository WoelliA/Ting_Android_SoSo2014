package de.ur.mi.android.ting.utilities;

import java.util.Collection;

import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.SearchResult;

public interface IDoneCallback<T> {
	public void done(T result);

	public boolean getIsDone();
	public void setIsDone(boolean done);

	public void fail(Exception object);


}
