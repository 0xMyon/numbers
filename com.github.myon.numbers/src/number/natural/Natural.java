package number.natural;

import number.integer.Integer;
import number.integer.Negative;
import number.nothing.NaN;
import util.Tuple;

public interface Natural extends Integer {

	static Natural of(long i) {
		if (i < 0) {
			return NONE;
		} else if (i == 0) {
			return ZERO;
		} else if (i % 2 == 0) {
			return of(i/2).twice();
		} else {
			return of(i-1).increment();
		}
	}
	
	
	Positive increment();
	Even twice();
	Natural square();
	
	default Natural absolute() {
		return this;
	}
	
	
	default Increment power(Zero that) {
		return ONE;
	}
	
	default boolean isBigger(Negative that) {
		return true;
	}
	default boolean isSmaler(Negative that) {
		return false;
	}
	default boolean isEqual(Negative that) {
		return false;
	}
	
	default Natural power(Natural that) {
		return that.accept(new Natural.Visitor<Natural>() {
			@Override
			public Natural handle(NaN that) {
				return that;
			}
			@Override
			public Natural handle(Double that) {
				return power(that.half()).square();
			}
			@Override
			public Natural handle(Increment that) {
				return power(that.decrement()).multiply(Natural.this);
			}
			@Override
			public Natural handle(Zero that) {
				return power(that);
			}
		});
	}
	
	default Natural subtract(Zero that) {
		return this;
	}
	Integer subtract(Positive that);
	
	default Integer subtract(Natural that) {
		return that.accept(new Visitor<Integer>() {
			@Override
			public NaN handle(NaN that) {
				return that;
			}
			@Override
			public Natural handle(Zero that) {
				return subtract(that);
			}
			@Override
			public Integer handle(Increment that) {
				return subtract(that);
			}
			@Override
			public Integer handle(Double that) {
				return subtract(that);
			}
		});
	}
	
	
	//Natural DECREMENT();
	/*
	default Natural SUBTRACT(Zero that) {
		return this;
	}
	Natural SUBTRACT(Positive that);
	
	default Natural SUBTRACT(Natural that) {
		return that.accept(new Visitor<Natural>() {
			@Override
			public NaN handle(NaN that) {
				return that;
			}
			@Override
			public Natural handle(Zero that) {
				return SUBTRACT(that);
			}
			@Override
			public Natural handle(Increment that) {
				return SUBTRACT(that);
			}
			@Override
			public Natural handle(Double that) {
				return SUBTRACT(that);
			}
		});
	}
	*/
		
	default Natural add(Natural that) {
		return that.accept(new Visitor<Natural>() {
			@Override
			public NaN handle(NaN that) {
				return that;
			}
			@Override
			public Natural handle(Zero that) {
				return add(that);
			}
			@Override
			public Positive handle(Increment that) {
				return add(that);
			}
			@Override
			public Positive handle(Double that) {
				return add(that);
			}
		});
	}

	Positive add(Positive that);
	
	default Natural add(Zero that) {
		return this;
	}
	
	default Natural multiply(Natural that) {
		return that.accept(new Visitor<Natural>() {
			@Override
			public NaN handle(NaN that) {
				return that;
			}
			@Override
			public Zero handle(Zero that) {
				return multiply(that);
			}
			@Override
			public Natural handle(Increment that) {
				return multiply(that);
			}
			@Override
			public Natural handle(Double that) {
				return multiply(that);
			}
		});
	}
	
	Natural multiply(Positive that);
	
	default Zero multiply(Zero that) {
		return that;
	}

	
	
	Natural euklid(Natural that);
	
	default Tuple<Natural,Natural> divideAndReminder(Positive that) {
		if (that.isBigger(this))
			return Tuple.of(ZERO, this);
		else {
			Tuple<Natural,Natural> x = divideAndReminder(that.twice());
			if (!x.second().isSmaler(that))
				return Tuple.of(x.first().twice().increment(), (Natural) x.second().subtract(that));
			else 
				return Tuple.of(x.first().twice(), x.second());
		}
	}
	
	
	default Natural modulo(Natural that) {
		return that.accept(new Visitor<Natural>() {
			@Override
			public NaN handle(NaN that) {
				return that;
			}
			@Override
			public Natural handle(Double that) {
				return divideAndReminder(that).second();
			}
			@Override
			public Natural handle(Increment that) {
				return divideAndReminder(that).second();
			}
			@Override
			public NaN handle(Zero that) {
				return NONE;
			}
		});
	}
	
	default Natural DIVIDE(Natural that) {
		return that.accept(new Visitor<Natural>() {
			@Override
			public NaN handle(NaN that) {
				return that;
			}
			@Override
			public Natural handle(Double that) {
				return divideAndReminder(that).first();
			}
			@Override
			public Natural handle(Increment that) {
				return divideAndReminder(that).first();
			}

			@Override
			public NaN handle(Zero that) {
				return NONE;
			}
		});
	}
	
	
	
	
	default <T> T accept(Integer.Visitor<T> visitor) {
		return visitor.handle(this);
	}
	
	<T> T accept(Visitor<T> visitor);
	
	interface Visitor<T> extends Positive.Visitor<T>, Even.Visitor<T> {
		@Override 
		T handle(Double that);
		T handle(Increment that);
		T handle(Zero that);
	}
	
}
