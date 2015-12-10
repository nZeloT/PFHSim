package sim.warehouse;

import sim.abstraction.StoreableType;

public interface StorageObject<T extends StoreableType> {
	public int getVolume();
	public T getType();
	public int getCosts();
}
