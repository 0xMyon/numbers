package number.integer;

import number.natural.Natural;
import number.natural.Positive;
import number.nothing.NaN;
import number.rational.Rational;

public interface Integer extends Rational {

	static Integer of(long i) {
		if (i < 0) {
			if (i == Long.MIN_VALUE)
				return of(i/2).twice();
			else
				return of(-i).negate();
		} else {
			return Natural.of(i); 
		}
	}
	

	
	Integer increment();
	Integer decrement();
	
	
	
	Integer twice();
	
	Integer square();
	
	Integer negate();
	
	default Integer enuminator() {
		return this;
	}
	default Positive denuminator() {
		return ONE;
	}
	@Override
	default Integer integer() {
		return this;
	}
	@Override
	default Rational rational() {
		return ZERO;
	}
	
	default Integer add(Negative that) {
		return subtract(that.negate());
	}
	
	Integer add(Natural that);
	
	default Integer add(Integer that) {
		return that.accept(new Visitor<Integer>() {
			@Override
			public Integer handle(Natural that) {
				return add(that);
			}
			@Override
			public Integer handle(Negative that) {
				return add(that);
			}
			@Override
			public NaN handle(NaN that) {
				return that;
			}
		});
	}
	
	default Integer subtract(Negative that) {
		return add(that.negate());
	}
	
	Integer subtract(Natural that);
	
	default Integer subtract(Integer that) {
		return that.accept(new Visitor<Integer>() {
			@Override
			public Integer handle(Natural that) {
				return subtract(that);
			}
			@Override
			public Integer handle(Negative that) {
				return subtract(that);
			}
			@Override
			public NaN handle(NaN that) {
				return that;
			}
		});
	}
	
	default Integer multiply(Negative that) {
		return multiply(that.negate()).negate();
	}
	
	Integer multiply(Natural that);
	
	default Integer multiply(Integer that) {
		return that.accept(new Visitor<Integer>() {
			@Override
			public Integer handle(Natural that) {
				return multiply(that);
			}
			@Override
			public Integer handle(Negative that) {
				return multiply(that);
			}
			@Override
			public NaN handle(NaN that) {
				return that;
			}
		});
	}
	
	Integer DIVIDE(Natural that);
	
	Integer modulo(Natural that);
	
	default Integer DIVIDE(Negative that) {
		return DIVIDE(that.negate()).negate();
	}
	
	default Integer DIVIDE(Integer that) {
		return that.accept(new Visitor<Integer>() {
			@Override
			public Integer handle(Natural that) {
				return DIVIDE(that);
			}
			@Override
			public Integer handle(Negative that) {
				return DIVIDE(that);
			}
			@Override
			public NaN handle(NaN that) {
				return that;
			}
		});
	}
	
	Integer power(Natural that);
	
	/**
	 * Absolute value of an {@link Integer}
	 * {@link #sign()} * {@link #absolute()} = id
	 * @return -i if i < 0; i if i >= 0
	 */
	Natural absolute();
	
	/**
	 * Sign of an {@link Integer}
	 * {@link #sign()} * {@link #absolute()} = id
	 * @return 1 if i > 0; 0 if i == 0; -1 if i < 0
	 */
	Integer sign();
	
	boolean isBigger(Natural that);
	boolean isSmaler(Natural that);
	boolean isEqual(Natural that);
	
	boolean isBigger(Negative that);
	boolean isSmaler(Negative that);
	boolean isEqual(Negative that);
	
	default boolean isBigger(Integer that) {
		return that.accept(new Integer.Visitor<Boolean>() {
			@Override
			public Boolean handle(Negative that) {
				return isBigger(that);
			}
			@Override
			public Boolean handle(Natural that) {
				return isBigger(that);
			}
			@Override
			public Boolean handle(NaN that) {
				throw that;
			}
		});
	}
	default boolean isSmaler(Integer that) {
		return that.accept(new Integer.Visitor<Boolean>() {
			@Override
			public Boolean handle(Negative that) {
				return isSmaler(that);
			}
			@Override
			public Boolean handle(Natural that) {
				return isSmaler(that);
			}
			@Override
			public Boolean handle(NaN that) {
				throw that;
			}
		});
	}
	default boolean isEqual(Integer that) {
		return that.accept(new Integer.Visitor<Boolean>() {
			@Override
			public Boolean handle(Negative that) {
				return isEqual(that);
			}
			@Override
			public Boolean handle(Natural that) {
				return isEqual(that);
			}
			@Override
			public Boolean handle(NaN that) {
				throw that;
			}
		});
	}
	
	
	
	default <T> T accept(Rational.Visitor<T> visitor) {
		return visitor.handle(this);
	}
	
	<T> T accept(Visitor<T> visitor);
	
	interface Visitor<T> {
		T handle(Negative that);
		T handle(Natural that);
		T handle(NaN that);
	}

	int toInt();
	
}
