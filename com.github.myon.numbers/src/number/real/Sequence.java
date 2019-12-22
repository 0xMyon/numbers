package number.real;

import java.util.function.BiFunction;

import number.Number;
import number.natural.Natural;
import number.rational.Fraction;
import number.rational.Rational;

public class Sequence<T> {

	private final T current;
	private final Natural n;
	private final BiFunction<Natural, T, T> function;
			
	private Sequence(Natural n, T initial, BiFunction<Natural, T, T> function) {
		this.n = n;
		this.current = initial;
		this.function = function;
	}
	
	public Sequence<T> next() {
		final Natural m = n.increment();
		return new Sequence<T>(m, function.apply(m, current), function);
	}
		
	public T current() {
		return current;
	}
	
	public static Sequence<Rational> exp(Rational x) { 
		return new Sequence<Rational>(
			Number.ZERO, 
			Number.ONE, 
			(n,c) -> c.multiply(x).divide(n)				
		);
	}
	
	// x/1! - x^3/3! + x^5/5! + ...
	public static Sequence<Rational> sin(Rational x) { 
		final Rational x_2 = x.square();
		return new Sequence<Rational>(
			Number.ZERO, 
			x, 
			(n,c) -> c.multiply(x_2).divide(n.square().twice().twice().add(n.twice())).negate()				
		);
	}
	
	// x^0/0! - x^2/2! + x^4/4! + ...
	public static Sequence<Rational> cos(Rational x) { 
		final Rational x_2 = x.square();
		return new Sequence<Rational>(
			Number.ZERO, 
			Number.ONE, 
			(n,c) -> c.multiply(x_2).divide(n.square().twice().twice().subtract(n.twice())).negate()			
		);
	}
	
	public static Sequence<Rational> ln(Rational x) { 
		return new Sequence<Rational>(
			Number.ONE, 
			x.decrement(), 
			(n,c) -> c.multiply(n.decrement()).divide(n).multiply(x.decrement()).negate()			
		);
	}

	@SuppressWarnings("unchecked")
	Sequence<T> multiply(T that, BiFunction<T, T, T> multiply) {
		return new Sequence<T>(n, current, function) {
			@Override
			public T current() {
				return multiply.apply(super.current(), that);
			}
		};
	}

	// (x0 + ... + xn) * (y0 +...+ ym)
	// x0*y0 + x0*(y1 +...+ ym) + y0*(x1 +...+ xn) + (x1 +...+ xn)*(y1 +...+ ym)
	public Sequence<Sequence<T>> multiply(Sequence<T> that) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// (x0 + y0) + ... + (xn + yn)
	public Sequence<T> add(Sequence<T> that) {
		
		return new Sequence<T>() {

			@Override
			public void next() {
				this.next();
				that.next();
			}

			@Override
			public T current() {
				return this.current().add(that.current());
			}
			
		};
		
	}

	
}
