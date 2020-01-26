package number.nothing;

import java.util.Objects;

import number.Printer;
import number.integer.Integer;
import number.integer.Negative;
import number.natural.Double;
import number.natural.Even;
import number.natural.Increment;
import number.natural.Natural;
import number.natural.Positive;
import number.natural.Zero;
import number.rational.Fraction;
import number.rational.Rational;

public class NaN extends RuntimeException implements Zero, Increment, Double, Negative, Fraction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public NaN twice() {
		return this;
	}

	@Override
	public NaN negate() {
		return this;
	}
	
	@Override
	public NaN invert() {
		return this;
	}

	@Override
	public NaN absolute() {
		return this;
	}

	@Override
	public NaN decrement() {
		return this;
	}

	@Override
	public NaN sign() {
		return this;
	}

	@Override
	public NaN square() {
		return this;
	}

	@Override
	public NaN increment() {
		return this;
	}
	
	public <T> T accept(Positive.Visitor<T> visitor) {
		return visitor.handle(this);
	}
	public <T> T accept(Even.Visitor<T> visitor) {
		return visitor.handle(this);
	}
	public <T> T accept(Natural.Visitor<T> visitor) {
		return visitor.handle(this);
	}
	public <T> T accept(Integer.Visitor<T> visitor) {
		return visitor.handle(this);
	}
	public <T> T accept(Rational.Visitor<T> visitor) {
		return visitor.handle(this);
	}

	@Override
	public NaN add(Even that) {
		return this;
	}

	@Override
	public NaN add(Double that) {
		return this;
	}

	@Override
	public NaN add(Increment that) {
		return this;
	}

	@Override
	public NaN add(Positive that) {
		return this;
	}

	@Override
	public NaN multiply(Double that) {
		return this;
	}

	@Override
	public NaN multiply(Increment that) {
		return this;
	}

	@Override
	public NaN multiply(Positive that) {
		return this;
	}
	@Override
	public NaN multiply(Natural that) {
		return this;
	}

	@Override
	public NaN half() {
		return this;
	}

	@Override
	public NaN power(Natural that) {
		return this;
	}

	@Override
	public NaN add(Natural that) {
		return this;
	}

	@Override
	public NaN subtract(Positive that) {
		return this;
	}

	@Override
	public NaN subtract(Increment that) {
		return this;
	}

	@Override
	public NaN subtract(Double that) {
		return this;
	}

	@Override
	public NaN subtract(Natural that) {
		return this;
	}

	
	public boolean isEqual(Natural that) {
		return that.accept(new Natural.Visitor<Boolean>() {
			@Override
			public Boolean handle(NaN that) {
				return true;
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
	
	@Override
	public boolean isSmaler(Natural that) {
		throw this;
	}

	@Override
	public boolean isBigger(Natural that) {
		throw this;
	}
	
	public String toString() {
		return Printer.toString(this);
	}
	public int hashCode() {
		return Objects.hash();
	}
	public boolean equals(Object object) {
		return object instanceof NaN;
	}
	

	@Override
	public NaN SUBTRACT(Positive that) {
		return this;
	}

	/*
	@Override
	public NaN SUBTRACT(Increment that) {
		return this;
	}

	@Override
	public NaN SUBTRACT(Double that) {
		return this;
	}
	*/

	@Override
	public NaN euklid(Natural that) {
		return this;
	}

	@Override
	public NaN DIVIDE(Natural that) {
		return this;
	}

	@Override
	public boolean isEqual(Negative that) {
		return that instanceof NaN;
	}

	@Override
	public boolean isSmaler(Negative that) {
		throw this;
		}

	@Override
	public boolean isBigger(Negative that) {
		throw this;
	}

	public static NaN of() {
		return new NaN();
	}
	
	public int toInt() {
		throw this;
	}

	@Override
	public NaN rational() {
		return this;
	}

	@Override
	public NaN integer() {
		return this;
	}

	@Override
	public NaN modulo(Natural that) {
		return this;
	}

	@Override
	public NaN DECREMENT() {
		return this;
	}

	@Override
	public NaN faculty() {
		return this;
	}

}
