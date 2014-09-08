package de.ur.mi.android.ting.app;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.app.activities.MainActivity;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.utilities.IAppStart;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;
import de.ur.mi.android.ting.utilities.initialization.CompositeInitializeable;
import de.ur.mi.android.ting.utilities.initialization.IInitializeable;
import de.ur.mi.android.ting.utilities.initialization.InitializeableProvider;

@Module(library = true, complete = false)
public class _AppModule {

	@Provides
	IAppStart provideIAppStart() {
		return new IAppStart() {

			@Override
			public Class<MainActivity> getStartActivityClass() {
				return MainActivity.class;
			}
		};
	}

	@Provides
	IInitializeable provideIInitializeable(
			final ICategoryProvider categoryProvider,
			final IUserService userService) {
		InitializeableProvider provider = new InitializeableProvider();

		// category initializeable
		provider.addInitializeable(new IInitializeable() {

			@Override
			public void initialize(final IDoneCallback<Void> callback) {
				categoryProvider
						.getAllCategories(new SimpleDoneCallback<List<Category>>() {
							@Override
							public void done(List<Category> result) {
								userService.checkIsLoggedIn();
								callback.done(null);
							}
						});
			}
		});
		// end category initializeable

		return new CompositeInitializeable(provider);
	}
}
