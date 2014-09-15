package de.ur.mi.android.ting.utilities;

public abstract class SimpleDoneCallback<T> implements IDoneCallback<T> {

	private boolean isDone;

	@Override
	public boolean getIsDone() {
		return this.isDone;
	}

	@Override
	public void setIsDone(boolean done) {
		this.isDone = done;
	}
	
	@Override
	public void fail(Exception e){
		if(!this.handleException(e)) {
			this.done(null);
		}
	}

	protected boolean handleException(Exception e) {
		return false;
	}
	
}
