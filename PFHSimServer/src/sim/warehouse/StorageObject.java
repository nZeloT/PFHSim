package sim.warehouse;

public interface StorageObject<T> {
	public int getVolume();
	public T getType();
	public int getCosts();
}
