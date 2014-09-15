package de.ur.mi.android.ting.model.primitives;

public class PagingRequestBase {

	private int offset;
	private int count;
	
	public PagingRequestBase(int offset, int count) {
		this.offset = offset;
		this.count = count;
	}	
	
	public int getCount() {
		return this.count;
	}
	public int getOffset() {
		return this.offset;
	}
}
