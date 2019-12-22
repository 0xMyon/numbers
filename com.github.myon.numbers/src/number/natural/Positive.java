package number.natural;

import number.integer.Negative;
import number.integer.Integer;
import number.nothing.NaN;
import number.rational.Fraction;
import number.rational.Rational;

public interface Positive extends Natural {

	Positive increment();
	Natural decrement();
	
	default Natural DECREMENT() {
		return decrement();
	}
	
	default Double twice() {
		return Double.of(this);
	}
	
	default Positive square() {
		return this.multiply(this);
	}
	
	Positive multiply(Positive that);
	
	default Negative negate() {
		return Negative.of(this); 
	}
	
	default Positive sign() {
		return ONE;
	}
	
	default Rational invert() {
		return Fraction.of(ONE, this);
	}
		
	default Positive subtract(Zero that) {
		return this;
	}
	Integer subtract(Increment that);
	Integer subtract(Double that);
	
	default Integer subtract(Positive that) {
		return that.accept(new Natural.Visitor<Integer>() {
			@Override
			public NaN handle(NaN that) {
				return that;
			}
			@Override
			public Positive handle(Zero that) {
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
	
	/*
	Natural SUBTRACT(Increment that);
	Natural SUBTRACT(Double that);
	
	default Natural SUBTRACT(Positive that) {
		return that.accept(new Natural.Visitor<Natural>() {
			@Override
			public NaN handle(NaN that) {
				return that;
			}
			@Override
			public Positive handle(Zero that) {
				return Positive.this;
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
	
	
	default Positive add(Zero that) {
		return this;
	}
	
	Positive add(Positive that);
		
	default Positive add(Natural that) {
		return that.accept(new Natural.Visitor<Positive>() {
			@Override
			public NaN handle(NaN that) {
				return that;
			}
			@Override
			public Positive handle(Zero that) {
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
	
	default Positive power(Natural that) {
		return that.accept(new Natural.Visitor<Positive>() {
			@Override
			public Positive handle(NaN that) {
				return that;
			}
			@Override
			public Positive handle(Double that) {
				return power(that.half()).square();
			}
			@Override
			public Positive handle(Increment that) {
				return multiply(power(that.decrement()));
			}
			@Override
			public Positive handle(Zero that) {
				return ONE;
			}
		});
	}
	
	
	default Natural euklid(Natural that) {
		return that.modulo(this).euklid(this);
	}
	
	default <T> T accept(Natural.Visitor<T> visitor) {
		return accept((Visitor<T>)visitor);
	}
	
	<T> T accept(Visitor<T> visitor);
	
	interface Visitor<T> {
		T handle(Double that);
		T handle(Increment that);
		T handle(NaN that);
	}
	
}
