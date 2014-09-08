package de.ur.mi.android.ting.app;

import dagger.ObjectGraph;

public class GraphInjector implements IInjector {

	private ObjectGraph graph;

	public GraphInjector(ObjectGraph graph) {
		this.graph = graph;
	}

	@Override
	public void inject(Object obj) {
		if (obj instanceof IInjectable) {
			if (((IInjectable) obj).skipInject())
				return;
		}
		graph.inject(obj);
	}

}
