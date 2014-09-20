package de.ur.mi.android.ting.app.controllers;

import java.lang.ref.WeakReference;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

import android.util.Log;
import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.app.ISelectedListener;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.utilities.IConnectivity;
import de.ur.mi.android.ting.utilities.IMultiSelectionListener;
import de.ur.mi.android.ting.utilities.IMultiSelectionManager;
import de.ur.mi.android.ting.utilities.WeakReferenceListenerHub;
import de.ur.mi.android.ting.utilities.cache.WeakRefMemoryCache;

@Singleton
public class MultiSelectCategoriesController extends CategoriesController
		implements IMultiSelectionListener<Category> {

	private IMultiSelectionManager<Category> manager;
	private WeakReferenceListenerHub<IChangeListener<Collection<Category>>> listenerHub = new WeakReferenceListenerHub<IChangeListener<Collection<Category>>>();
	private boolean doNotify = true;

	private static WeakReference<MultiSelectCategoriesController> current;

	public static MultiSelectCategoriesController current() {
		return current.get();
	}

	public Collection<Category> getSelectedCategories() {
		return manager.getSelectedItems();
	}

	@Inject
	public MultiSelectCategoriesController(ICategoryProvider categoryProvider,
			IConnectivity connectivity, IUserService userService, LocalUser user) {
		super(categoryProvider, connectivity, userService, user);

	}

	@Override
	public void onSelected(Category selectedItem) {
		if (doNotify) {
			this.notifyListener();
		}
	}

	private void notifyListener() {
		for (IChangeListener<Collection<Category>> listener : listenerHub) {
			listener.onChange(manager.getSelectedItems());
		}
	}

	@Override
	public void onUnSelect(Category unselectedItem) {
		this.notifyListener();
	}

	@Override
	public void setManager(IMultiSelectionManager<Category> manager) {
		this.manager = manager;
		doNotify = false;
		if (user.getFavoriteCategories() != null) {
			for (Category category : user.getFavoriteCategories()) {
				manager.setSelected(category, true);
			}
		}
		doNotify = true;
		notifyListener();
	}

	public void addChangeListener(IChangeListener<Collection<Category>> listener) {
		listenerHub.addListener(listener);
	}
}
