package de.ur.mi.android.ting.model.parse;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import de.ur.mi.android.ting.model.primitives.PagingRequestBase;
import de.ur.mi.android.ting.model.primitives.SearchRequest;

public class ParseQueryHelper {
	public static ParseQuery<ParseObject> addWhereContains(
			ParseQuery<ParseObject> parseQuery, String[] searchedFields,
			String containingString) {

		List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
		for (String field : searchedFields) {
			ParseQuery<ParseObject> whereMatches = parseQuery.whereMatches(
					field, containingString, "im");
			whereMatches.setLimit(0);
			whereMatches.setSkip(0);
			queries.add(whereMatches);
		}
		return ParseQuery.or(queries);
	}

	public static ParseQuery<ParseObject> getPagingQuery(String className,
			PagingRequestBase request) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(className);
		query.setLimit(request.getCount());
		query.setSkip(request.getOffset());
		return query;
	}

	public static ParseQuery<ParseObject> getSearchQuery(String className,
			SearchRequest request, String[] searchedFields) {
		List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
		for (String string : searchedFields) {
			ParseQuery<ParseObject> subQuery = new ParseQuery<ParseObject>(
					className);
			subQuery = subQuery.whereMatches(string, request.getQuery(), "im");
			queries.add(subQuery);
		}

		ParseQuery<ParseObject> searchQuery = ParseQuery.or(queries);
		searchQuery.setLimit(request.getCount());
		searchQuery.setSkip(request.getOffset());
		return searchQuery;
	}
}
