package de.ur.mi.android.ting.utilities;

public interface IDoneCallback<T> {
	public void done(T result);

	public boolean getIsDone();
	public void setIsDone(boolean done);


}
