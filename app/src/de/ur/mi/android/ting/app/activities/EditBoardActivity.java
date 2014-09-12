package de.ur.mi.android.ting.app.activities;

import java.util.Collection;

import javax.inject.Inject;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.adapters.ViewCreationDelegatingListAdapter;
import de.ur.mi.android.ting.app.controllers.BoardEditRequest;
import de.ur.mi.android.ting.app.controllers.EditBoardController;
import de.ur.mi.android.ting.app.controllers.EditBoardController.EditBoardView;
import de.ur.mi.android.ting.app.viewResolvers.CategoryViewResolver;
import de.ur.mi.android.ting.model.SpecialCategories;
import de.ur.mi.android.ting.model.SpecialCategories.SpecialCategory;
import de.ur.mi.android.ting.model.primitives.Board;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.utilities.IBiChangeListener;
import de.ur.mi.android.ting.utilities.view.Notify;
import de.ur.mi.android.ting.utilities.view.ViewResolver;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class EditBoardActivity extends BaseActivity implements EditBoardView {

	public static final String TYPE_KEY = "type";
	public static final String BOARD_ID_KEY = "boardId";

	@Inject
	public EditBoardController controller;
	private EditText titleView;
	private EditText descriptionView;
	private Spinner categorySelect;
	private ViewCreationDelegatingListAdapter<Category> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_board);

		int type = getIntent().getExtras().getInt(TYPE_KEY);

		initUi();

		controller.setView(this);
		if (type == TYPE_EDIT) {
			String boardId = getIntent().getExtras().getString(BOARD_ID_KEY);
			controller.init(boardId);
		}
	}

	private void initUi() {
		this.categorySelect = (Spinner) this
				.findViewById(R.id.edit_board_category_select);

		this.titleView = (EditText) this.findViewById(R.id.edit_board_title);
		this.descriptionView = (EditText) this
				.findViewById(R.id.edit_board_description);

		this.categorySelect.getSelectedItemPosition();

		((Button) this.findViewById(R.id.edit_board_save))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						EditBoardActivity.this.saveBoard();
					}
				});
	}

	protected void saveBoard() {
		String title = this.titleView.getText().toString();

		if (title == null || title.equals("")) {
			this.titleView.setError(this
					.getString(R.string.error_field_required));
			return;
		}
		Object selectedItem = this.categorySelect.getSelectedItem();
		if (selectedItem == null || selectedItem instanceof SpecialCategory) {
			Notify.current().showToast(R.string.label_select_category);
			return;
		}
		String description = this.descriptionView.getText().toString();
		controller.saveBoard(title, description, (Category) selectedItem);
	}

	@Override
	public void showBoardDetails(Board board) {
		this.titleView.setText(board.getTitle());
		this.descriptionView.setText(board.getDescription());
	}

	@Override
	public void onBoardSaved(BoardEditRequest request) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setupCategories(Collection<Category> result) {
		ViewResolver<Category> resolver = new SimpleCategoryViewResolver(this);
		adapter = new ViewCreationDelegatingListAdapter<Category>(this,
				resolver);

		adapter.add(new SpecialCategories.SpecialCategory(0, this
				.getString(R.string.label_select_category)));
		adapter.addAll(result);
		this.categorySelect.setAdapter(adapter);
	}

	private class SimpleCategoryViewResolver extends CategoryViewResolver {

		public SimpleCategoryViewResolver(Context context) {
			super(R.layout.category_layout, context, null);
		}

		@Override
		protected void decorateView(View view, Category category,
				ViewGroup parent) {
			super.decorateView(view, category, parent);
			View button = this
					.findViewById(view, R.id.category_favorite_button);
			button.setVisibility(View.INVISIBLE);
		}

		@Override
		public boolean skipInject() {
			return true;
		}
	}
}
