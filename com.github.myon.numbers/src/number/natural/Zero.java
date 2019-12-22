package number.natural;

import java.util.Objects;

import number.integer.Negative;
import number.nothing.NaN;

public interface Zero extends Even {

	default Increment increment() {
		return Increment.of(this);
	}
	
	default Negative decrement() {
		return increment().negate();
	}
	
	default Zero negate() {
		return this;
	}
	
	default Zero sign() {
		return this;
	}
	
	default Zero twice() {
		return this;
	}
	
	default Zero half() {
		return this;
	}
	
	default Zero square() {
		return this;
	}
	
	default NaN invert() {
		return NONE;
	}
	
	default Negative subtract(Positive that) {
		return that.negate();
	}
	
	default Natural SUBTRACT(Positive that) {
		// TODO maybe this is not what we want
		return that;
	}
	
	default Natural DECREMENT() {
		return ONE;
	}
	
	default Double add(Double that) {
		return that;
	}
	
	default Increment add(Increment that) {
		return that;
	}
	
	default Even add(Even that) {
		return that;
	}
	
	default Zero multiply(Even that) {
		return this;
	}
	default Zero multiply(Double that) {
		return this;
	}
	default Zero multiply(Increment that) {
		return this;
	}
	
	// TODO 0%0 != NaN
	default Zero modulo(Natural that) {
		return this;
	}
	
	default NaN power(Zero that) {
		return NONE;
	}
	
	default Natural euklid(Natural that) {
		return that;
	}
	
	
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
				return false;
			}
			@Override
			public Boolean handle(Zero that) {
				return true;
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
				return true;
			}
			@Override
			public Boolean handle(Increment that) {
				return true;
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
				return false;
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
	
	static Zero of() {
		return new Zero() {
			@Override
			public <T> T accept(Visitor<T> visitor) {
				return visitor.handle(this);
			}
			public String toString() {
				return "0";
			}
			public int hashCode() {
				return Objects.hash();
			}
			public boolean equals(Object that) {
				return that instanceof Zero && !(that instanceof NaN);
			}
			
		};
	}
	
	default int toInt() {
		return 0;
	}
	
}
