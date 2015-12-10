package sim.warehouse;

import sim.abstraction.StorebleType;

public interface StorageObject<T extends StorebleType> {
	public int getVolume();
	public T getType();
	public int getCosts();
}
