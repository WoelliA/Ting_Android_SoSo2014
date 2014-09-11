package de.ur.mi.android.ting.model.primitives;

public class SearchRequest extends PagingRequestBase{
	private SearchType type;
	private String query;

	public SearchRequest(SearchType type, int offset, int count, String query) {
		super(offset, count);
		this.type = type;
		this.query = query;
	}

	public SearchType getType() {
		return this.type;
	}

	public String getQuery() {
		return this.query;
	}
}
