package sim;

public interface Event<T> {
	public void changed(T oldValue, T newValue);
}
