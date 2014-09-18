package de.ur.mi.android.ting.app.controllers;

import java.util.Collection;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.model.IBoardsService;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.utilities.IConnectivity;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;
import de.ur.mi.android.ting.utilities.view.Notify;
import de.ur.mi.android.ting.utilities.view.Notify.LoadingContext;

public class EditBoardController {
	public static interface EditBoardView {
		public static final int TYPE_CREATE = 1;
		public static final int TYPE_EDIT = 0;

		void showBoardDetails(Board board);

		void onBoardSaved(BoardEditRequest request);

		void setupCategories(Collection<Category> result);
	}

	private IBoardsService boardsService;
	private EditBoardView view;
	private String boardId;
	private IConnectivity connectivity;
	private ICategoryProvider categoryProvider;

	public EditBoardController(IBoardsService boardsService,
			ICategoryProvider categoryProvider, IConnectivity connectivy) {
		this.boardsService = boardsService;
		this.categoryProvider = categoryProvider;
		this.connectivity = connectivy;
	}

	public void setView(final EditBoardView view) {
		this.view = view;
		this.categoryProvider
				.getAllCategories(new SimpleDoneCallback<Collection<Category>>() {
					@Override
					public void done(Collection<Category> result) {
						view.setupCategories(result);
					}
				});
	}

	public void init(String boardId) {
		this.boardId = boardId;
		if (boardId != null && boardId.trim().length() != 0) {

			this.boardsService.getBoard(boardId,
					new SimpleDoneCallback<Board>() {

						@Override
						public void done(Board board) {
							EditBoardController.this.view
									.showBoardDetails(board);
						}
					});
		}
	}

	public void saveBoard(String title, String description, Category category) {
		if (!this.connectivity.hasWebAccess(true)) {
			return;
		}
		final LoadingContext loading = Notify.current().showLoading(
				R.string.loading_saving_board);

		final BoardEditRequest request = new BoardEditRequest(this.boardId,
				title, description, category.getId());

		IDoneCallback<Void> callback = new SimpleDoneCallback<Void>() {

			@Override
			public void done(Void searchResult) {
				loading.close();
				Notify.current().showToast(R.string.success_saving_board);
				EditBoardController.this.view.onBoardSaved(request);
			}

			@Override
			protected boolean handleException(Exception e) {
				Notify.current().showToast(e.getMessage());
				return true;
			}
		};

		if (this.boardId == null) {
			this.boardsService.createBoard(request, callback);
		} else {
			this.boardsService.saveBoard(request, callback);
		}

	}
}
