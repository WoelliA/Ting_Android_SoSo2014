package de.ur.mi.android.ting.model.primitives;

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
			IUnique other = (IUnique) o;
			if (this.id != null) {
				return this.id.equals(other.getId());
			}
		}
		return super.equals(o);
	}
}
