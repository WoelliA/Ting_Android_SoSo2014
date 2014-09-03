package de.ur.mi.android.ting.utilities.initialization;

import java.util.List;

import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

public class CompositeInitializeable implements IInitializeable {

	private IInitializeableProvider provider;
	protected int initializedCount;

	public CompositeInitializeable(IInitializeableProvider provider) {
		this.provider = provider;
		this.initializedCount = 0;
	}

	@Override
	public void initialize(final IDoneCallback<Void> callback) {

		final List<IInitializeable> initializeables = this.provider
				.getInitializeables();
		for (IInitializeable initializeable : initializeables) {
			initializeable.initialize(new SimpleDoneCallback<Void>() {

				@Override
				public void done(Void result) {
					CompositeInitializeable.this.initializedCount += 1;
					if (CompositeInitializeable.this.initializedCount == initializeables.size()) {
						callback.done(null);
					}
				}
			});
		}
	}
}
