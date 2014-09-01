package de.ur.mi.android.ting.utilities;

public abstract class SimpleDoneCallback<T> implements IDoneCallback<T> {

	private boolean isDone;

	@Override
	public boolean getIsDone() {
		return isDone;
	}

	@Override
	public void setIsDone(boolean done) {
		this.isDone = done;
	}

}
