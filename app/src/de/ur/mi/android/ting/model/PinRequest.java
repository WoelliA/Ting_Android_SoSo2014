package de.ur.mi.android.ting.model;

public class PinRequest {
	private int offset;
	private int count;
	
	public PinRequest(int offset, int count) {
		this.offset = offset;
		this.count = count;
	}

	public int getCount() {
		return count;
	}


	public int getOffset() {
		return offset;
	}

}
