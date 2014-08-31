package de.ur.mi.android.ting.app;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import de.ur.mi.android.ting.app.activities.MainActivity;
import de.ur.mi.android.ting.model.ICategoryProvider;
import de.ur.mi.android.ting.model.ICategoryReceivedCallback;
import de.ur.mi.android.ting.model.primitives.Category;
import de.ur.mi.android.ting.utilities.CompositeInitializeable;
import de.ur.mi.android.ting.utilities.IAppStart;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.IInitializeable;
import de.ur.mi.android.ting.utilities.InitializeableProvider;

@Module(library = true, complete = false)
public class AppModule {

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
			final ICategoryProvider categoryProvider) {
		InitializeableProvider provider = new InitializeableProvider();

		// category initializeable
		provider.addInitializeable(new IInitializeable() {

			@Override
			public void initialize(final IDoneCallback<Void> callback) {
				categoryProvider
						.getAllCategories(new ICategoryReceivedCallback() {

							@Override
							public void onCategoriesReceived(
									List<Category> categories) {
								callback.done(null);
							}
						});

			}
		});
		// end category initializeable

		return new CompositeInitializeable(provider);
	}
}
