package util;

public class Tuple<A,B> {

	private final A first;
	private final B second;
	
	Tuple(A first, B second) {
		this.first = first;
		this.second = second;
	}

	public static <A,B> Tuple<A,B> of(A a, B b) {
		return new Tuple<A, B>(a, b);
	}
	
	public A first() {
		return first;
	}

	public B second() {
		return second;
	}
	
	public String toString() {
		return first+","+second;
	}
	
}
