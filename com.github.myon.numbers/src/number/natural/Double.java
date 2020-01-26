package number.natural;

import java.util.Objects;

import number.Printer;
import number.integer.Integer;
import number.nothing.NaN;

public interface Double extends Even, Positive {
	
	default <T> T accept(Natural.Visitor<T> visitor) {
		return visitor.handle(this);
	}
	default <T> T accept(Even.Visitor<T> visitor) {
		return visitor.handle(this);
	}
	default <T> T accept(Positive.Visitor<T> visitor) {
		return visitor.handle(this);
	}
		
	@Override
	default Increment increment() {
		return Even.super.increment();
	}
	
	/**
	 * @see Increment#increment()
	 */
	default Increment decrement() {
		return half().decrement().twice().increment();
	}
	
	Positive half();
	
	// a*2 + b*2 = (a+b)*2
	default Double add(Even that) {
		return this.half().add(that.half()).twice();
	}
	default Double add(Double that) {
		return add((Even)that);
	}
	// a*2 + (b*2) +1
	default Increment add(Increment that) {
		return this.add(that.decrement()).increment();
	}
	
	default Positive add(Positive that) {
		return that.accept(new Positive.Visitor<Positive>() {
			@Override
			public Positive handle(Double that) {
				return add(that);
			}
			@Override
			public Positive handle(Increment that) {
				return add(that);
			}
			@Override
			public Positive handle(NaN that) {
				return that;
			}
		});
	}
	
	// 2a - (2b+1)
	default Integer subtract(Increment that) {
		return subtract(that.decrement()).decrement();
	}
	
	// 2a - 2b = 2(a-b)
	default Integer subtract(Double that) {
		return half().subtract(that.half()).twice();
	}
	
	/*
	// 2a - (2b+1)
	default Natural SUBTRACT(Increment that) {
		return SUBTRACT(that.decrement()).DECREMENT();
	}
	
	// 2a - 2b = 2(a-b)
	default Natural SUBTRACT(Double that) {
		return half().SUBTRACT(that.half()).twice();
	}
	*/
	
	// 2a * 2b = 4ab
	default Double multiply(Double that) {
		return half().multiply(that).twice();
	}
	default Double multiply(Increment that) {
		return half().multiply(that).twice();
	}
	
	
	default Double multiply(Positive that) {
		return that.accept(new Positive.Visitor<Double>() {
			@Override
			public Double handle(Double that) {
				return multiply(that);
			}
			@Override
			public Double handle(Increment that) {
				return multiply(that);
			}
			@Override
			public Double handle(NaN that) {
				return that;
			}
		});
	}
	
	default boolean isEqual(Natural that) {
		return that.accept(new Natural.Visitor<Boolean>() {
			@Override
			public Boolean handle(NaN that) {
				return false;
			}
			@Override
			public Boolean handle(Double that) {
				return half().isEqual(that.half());
			}
			@Override
			public Boolean handle(Increment that) {
				return false;
			}
			@Override
			public Boolean handle(Zero that) {
				return false;
			}
		});
	}
	
	default boolean isSmaler(Natural that) {
		return that.accept(new Natural.Visitor<Boolean>() {
			@Override
			public Boolean handle(NaN that) {
				throw new NaN();
			}
			@Override
			public Boolean handle(Double that) {
				return half().isSmaler(that.half());
			}
			@Override
			public Boolean handle(Increment that) {
				// (2a < 2b+1) <=> (2a <= 2b) <=> (2a !> 2b)
				return !isBigger(that.decrement());
			}
			@Override
			public Boolean handle(Zero that) {
				return false;
			}
		});
	}
	
	default boolean isBigger(Natural that) {
		return that.accept(new Natural.Visitor<Boolean>() {
			@Override
			public Boolean handle(NaN that) {
				throw new NaN();
			}
			@Override
			public Boolean handle(Double that) {
				return half().isBigger(that.half());
			}
			@Override
			public Boolean handle(Increment that) {
				// 2a > 2b+1 <=> 2a > 2b
				return isBigger(that.decrement());
			}
			@Override
			public Boolean handle(Zero that) {
				return true;
			}
		});
	}
	
	static Double of(Positive half) {
		return new Double() {
			@Override
			public Positive half() {
				return half;
			}
			public String toString() {
				return Printer.toString(this);
			}
			public int hashCode() {
				return Objects.hash(half);
			}
			public boolean equals(Object object) {
				if (object instanceof Double) {
					Double that = (Double) object;
					return half.equals(that.half());
				}
				return false;
			}
			

		};
	}
	
	default int toInt() {
		return half().toInt() * 2;
	}
	
}
