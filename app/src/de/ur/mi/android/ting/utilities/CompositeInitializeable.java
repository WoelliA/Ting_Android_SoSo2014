package de.ur.mi.android.ting.utilities;

import java.util.List;

public class CompositeInitializeable implements IInitializeable {

	private IInitializeableProvider provider;
	protected int initializedCount;

	public CompositeInitializeable(IInitializeableProvider provider) {
		this.provider = provider;
		initializedCount = 0;
	}

	@Override
	public void initialize(final IDoneCallback<Void> callback) {

		final List<IInitializeable> initializeables = provider
				.getInitializeables();
		for (IInitializeable initializeable : initializeables) {
			initializeable.initialize(new IDoneCallback<Void>() {

				@Override
				public void done(Void result) {
					initializedCount += 1;
					if (initializedCount == initializeables.size()) {
						callback.done(null);
					}
				}
			});
		}
	}
}
