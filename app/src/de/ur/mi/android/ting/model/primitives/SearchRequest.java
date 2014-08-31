package de.ur.mi.android.ting.model.primitives;

public class SearchRequest {
	private SearchType type;
	private int offset;
	private int count;
	private String query;

	public SearchRequest(SearchType type, int offset, int count, String query) {
		this.type = type;
		this.offset = offset;
		this.count = count;
		this.query = query;
		
	}
}
