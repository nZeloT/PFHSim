package ui.abstraction;

@FunctionalInterface
public interface Callable<V> {
	V call();
}
