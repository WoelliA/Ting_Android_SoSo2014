package de.ur.mi.android.ting.app.controllers;

import java.util.Collection;

import javax.inject.Inject;

import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.model.BoardSorting;
import de.ur.mi.android.ting.model.IBoardsService;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.PagingResult;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.model.primitives.UniqueBase;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.PagingDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public class BoardsController extends PagingController<Board> implements IChangeListener<Collection<Category>> {

	public IBoardsService boardsService;
	
	private Collection<Category> categories;

	@Inject 
	public BoardsController(IBoardsService boardsService, MultiSelectCategoriesController categoriesController) {
		this.boardsService = boardsService;
		categoriesController.addChangeListener(this);
	}

	@Override
	protected void loadNextPage(int offset,
			IDoneCallback<PagingResult<Board>> callback) {
		Collection<String> categoryIds = UniqueBase.getIds(categories);

		CategoryBoardsRequest request = new CategoryBoardsRequest(categoryIds,
				offset, 10, BoardSorting.Popular);
		
		PagingDoneCallback<Board> pagingCallback = new PagingDoneCallback<Board>(
				request, callback);

		boardsService.getBoardsForCategories(request, pagingCallback);
	}

	@Override
	public void onChange(Collection<Category> changed) {
		categories = changed;
		super.reset();		
	}
}
