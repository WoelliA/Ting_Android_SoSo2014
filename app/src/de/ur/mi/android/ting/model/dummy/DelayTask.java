package de.ur.mi.android.ting.model.dummy;

import android.os.AsyncTask;

public abstract class DelayTask extends AsyncTask<Void, Void, Void>{

	private long delayInMilliseconds;

	public DelayTask(int delayInMilliseconds) {
		this.delayInMilliseconds = delayInMilliseconds;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		try {
			Thread.sleep(this.delayInMilliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

}
