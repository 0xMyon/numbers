package number.natural;

import java.util.Objects;

import number.Printer;
import number.integer.Integer;
import number.nothing.NaN;

public interface Increment extends Positive {
	
	// ((n*2)+1)+1 = (n+1)*2
	/**
	 * @see Double#decrement()
	 */
	default Double increment() {
		return decrement().half().increment().twice();
	}
	
	// 2a+1 + 2b+1 = 2(a+b+1)
	default Double add(Increment that) {
		return this.decrement().half().add(that.decrement().half()).increment().twice();
	}
	
	// 2a + 2b+1
	default Increment add(Double that) {
		return decrement().add(that).increment();
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
	
	
	Even decrement();
	
	// 2a * 2b+1 = 2(2ab+a)
	default Double multiply(Double that) {
		return decrement().multiply(that).add(that);
	}
	
	// 2a+1 * 2b+1 = 2(2ab + a + b) + 1
	default Increment multiply(Increment that) {
		return decrement().multiply(that).add(that);
	}
	
	default Positive multiply(Positive that) {
		return that.accept(new Positive.Visitor<Positive>() {
			@Override
			public Positive handle(Double that) {
				return multiply(that);
			}
			@Override
			public Positive handle(Increment that) {
				return multiply(that);
			}
			@Override
			public Positive handle(NaN that) {
				return that;
			}
		});
	}
	
	// 2a+1 - 2b
	default Integer subtract(Increment that) {
		return decrement().subtract(that.decrement());
	}
	
	// 2a+1 - 2b+1 = 2a-2b
	default Integer subtract(Double that) {
		return decrement().subtract(that.decrement());
	}
	
	/*
	// 2a+1 - 2b
	default Natural SUBTRACT(Increment that) {
		return decrement().SUBTRACT(that.decrement());
	}
	
	// 2a+1 - 2b+1 = 2a-2b
	default Natural SUBTRACT(Double that) {
		return decrement().SUBTRACT(that.decrement());
	}
	*/
		
	
	
	default boolean isEqual(Natural that) {
		return that.accept(new Natural.Visitor<Boolean>() {
			@Override
			public Boolean handle(NaN that) {
				return false;
			}
			@Override
			public Boolean handle(Double that) {
				return false;
			}
			@Override
			public Boolean handle(Increment that) {
				return decrement().isEqual(that.decrement());
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
				// (2a+1 < 2b) <=> (2a < 2b)
				return decrement().isSmaler(that);
			}
			@Override
			public Boolean handle(Increment that) {
				return decrement().isSmaler(that.decrement());
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
				// (2a+1 > 2b) <=> (2a >= 2b) <=> (2a !< 2b)
				return !decrement().isSmaler(that);
			}
			@Override
			public Boolean handle(Increment that) {
				return decrement().isBigger(that.decrement());
			}
			@Override
			public Boolean handle(Zero that) {
				return true;
			}
		});
	}
	
	static Increment of(Even decrement) {
		return new Increment() {
			@Override
			public Even decrement() {
				return decrement;
			}
			public String toString() {
				return Printer.toString(this);
			}
			public int hashCode() {
				return Objects.hash(decrement);
			}
			public boolean equals(Object object) {
				if (object instanceof Increment) {
					Increment that = (Increment) object;
					return decrement.equals(that.decrement());
				}
				return false;
			}
			public <T> T accept(Positive.Visitor<T> visitor) {
				return visitor.handle(this);
			}
		};
	}
	
	default int toInt() {
		return decrement().toInt()+1;
	}


}
