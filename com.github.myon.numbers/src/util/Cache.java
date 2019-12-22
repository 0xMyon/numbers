package util;

public abstract class Cache<T> {

	private T value;
	
	T get() {
		if (null == value) {
			value = value();
		}
		return value;
	}
	
	public abstract T value();
	
}
