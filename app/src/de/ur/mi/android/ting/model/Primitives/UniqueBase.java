package de.ur.mi.android.ting.model.primitives;

import java.util.ArrayList;
import java.util.Collection;

public abstract class UniqueBase implements IUnique {
	protected String id;

	@Override
	public String getId() {
		return this.id;
	}

	private void setId(String id) {
		this.id = id;
	}

	public UniqueBase(String id) {
		this.setId(id);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof IUnique) {
			return this.hashCode() == o.hashCode();
		}
		return super.equals(o);
	}

	@Override
	public int hashCode() {
		if (this.id != null) {
			return this.id.hashCode();
		}
		return super.hashCode();
	}

	public static Collection<String> getIds(Collection<? extends IUnique> uniques) {
		ArrayList<String> ids = new ArrayList<String>();
		if(uniques != null){
			for (IUnique unique : uniques) {
				ids.add(unique.getId());
			}
		}		
		return ids;
		
	}
}
