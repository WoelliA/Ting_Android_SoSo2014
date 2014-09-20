package de.ur.mi.android.ting.app.controllers;

import java.util.Collection;

import de.ur.mi.android.ting.model.BoardSorting;
import de.ur.mi.android.ting.model.primitives.PagingRequestBase;

public class CategoryBoardsRequest extends PagingRequestBase{

	private Collection<String> categoryIds;
	private BoardSorting sorting;

	public CategoryBoardsRequest(Collection<String> categoryIds,int offset, int count, BoardSorting sorting) {
		super(offset, count);
		this.categoryIds = categoryIds;
		this.sorting = sorting;
	}

	public Collection<String> getCategoryIds() {
		return this.categoryIds;
	}
	
	public BoardSorting getSorting(){
		return this.sorting;
	}

}
