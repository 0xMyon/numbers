package number.real;

import java.util.stream.Stream;

import number.Number;
import number.natural.Natural;
import number.rational.Rational;
import util.Cache;

public abstract class Sequence {

	public abstract Sequence[] next(); 
	
	public abstract Rational current();
	
	
	
	public Sequence exp() {
		
		Stream.of(Sequence.this.next());
		// TODO
		//exp(Sequence.this.current());
		return null;
	}
	
	
	public static Sequence exp(Rational x) {
		return exp(x, Number.ZERO, Number.ONE);
	}
	
	private static Sequence exp(Rational x, Natural n, Rational current) {
		return new Sequence() {
			
			private Cache<Sequence> next = new Cache<Sequence>() {
				@Override
				public Sequence value() {
					x.next();
					final Natural n_1 = n.increment();
					return exp(x, n_1, current.multiply(x).divide(n_1));
				}
			};
			
			
			@Override
			public Sequence[] next() {
				return new Sequence[] {next.value()};
			}
			@Override
			public Rational current() {
				return current;
			}
			
			public String toString() {
				return "e^"+x.toString();
			}
		};
	}
	
		
	// x/1! - x^3/3! + x^5/5! + ...
	public static Sequence sin(Rational x) { 
		return trigo("sin("+x.toString()+")", x.square(), Number.ONE, Number.ONE);
	}
	
	// x^0/0! - x^2/2! + x^4/4! + ...
	public static Sequence cos(Rational x) {
		return trigo("cos("+x.toString()+")", x.square(), Natural.ZERO, Natural.ONE);
	}
	
	public static Sequence trigo(String f, Rational x_2, Natural n, Rational current) { 
		return new Sequence() {
			
			private Cache<Sequence> next = new Cache<Sequence>() {
				@Override
				public Sequence value() {
					final Natural n_1 = n.increment();
					final Natural n_2 = n_1.increment();
					x_2.next();
					return trigo(f, x_2, n_2, current.multiply(x_2).divide(n_1).divide(n_2).negate());
				}
			};
			
			
			@Override
			public Sequence[] next() {
				return new Sequence[] {next.value()};
			}
			@Override
			public Rational current() {
				return current;
			}
			
			public String toString() {
				return f;
			}
		};
	}
	
	public static Sequence ln(Rational x) { 
		final Rational xm1 = x.decrement(); 
		return ln("ln("+x.toString()+")", xm1, Natural.ZERO, xm1);
	}
	
	public static Sequence ln(String f, Rational xm1, Natural n, Rational current) { 
		return new Sequence() {
			
			private Cache<Sequence> next = new Cache<Sequence>() {
				@Override
				public Sequence value() {
					final Natural n_1 = n.increment();
					xm1.next();
					return ln(f, xm1, n_1, current.multiply(n).multiply(xm1).divide(n_1).negate());
				}
			};
			@Override
			public Sequence[] next() {
				return new Sequence[] {next.value()};
			}
			@Override
			public Rational current() {
				return current;
			}
			
			public String toString() {
				return f;
			}
		};
	}
	

	public Sequence multiply(Rational that) {
		return new Sequence() {
			
			final Rational current = Sequence.this.current().multiply(that);
			
			private Cache<Sequence[]> next = new Cache<Sequence[]>() {
				@Override
				public Sequence[] value() {
					return Stream.of(Sequence.this.next()).map(that::multiply).toArray(Sequence[]::new);
				}
			};
			
			@Override
			public Sequence[] next() {
				return next.value();
			}
			@Override
			public Rational current() {
				return current;
			}
			public String toString() {
				return Sequence.this.toString()+"*"+that.toString();
			}
		};
	}

	// (x0 + ... + xn) * (y0 +...+ ym)
	// x0*y0 + x0*(y1 +...+ ym) + y0*(x1 +...+ xn) + (x1 +...+ xn)*(y1 +...+ ym)
	public Sequence multiply(Sequence that) {
		return new Sequence() {
			
			final Rational current = Sequence.this.current().multiply(that.current());
			
			private Cache<Sequence[]> next = new Cache<Sequence[]>() {
				@Override
				public Sequence[] value() {
					final Sequence[] A = Sequence.this.next();
					final Sequence[] B = that.next();
					return Stream.concat(
						Stream.concat(
							Stream.of(A).map(that.current()::multiply),
							Stream.of(B).map(Sequence.this.current()::multiply)
						),
						Stream.of(A).map(
							a -> Stream.of(B).map(a::multiply)
						).reduce(Stream.of(), Stream::concat)
					).toArray(Sequence[]::new);
				}
			};
			
			@Override
			public Sequence[] next() {
				return next.value();
			}
			@Override
			public Rational current() {
				return current;
			}
			public String toString() {
				return Sequence.this.toString()+"*"+that.toString();
			}
		};
	}
	
}
