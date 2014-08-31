package de.ur.mi.android.ting.utilities;

import java.util.ArrayList;
import java.util.List;

public class InitializeableProvider implements IInitializeableProvider {

	private List<IInitializeable> initializeables;

	public InitializeableProvider() {
		this.initializeables = new ArrayList<IInitializeable>();
	}

	@Override
	public List<IInitializeable> getInitializeables() {
		return this.initializeables;
	}
	
	public void addInitializeable(IInitializeable initializeable){
		this.initializeables.add(initializeable);
	}

}
