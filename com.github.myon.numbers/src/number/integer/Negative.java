package number.integer;

import java.util.Objects;

import number.natural.Double;
import number.natural.Increment;
import number.natural.Natural;
import number.natural.Positive;
import number.natural.Zero;
import number.nothing.NaN;
import number.rational.Fraction;
import number.rational.Rational;

public interface Negative extends Integer {

	default Integer increment() {
		return negate().decrement().negate();
	}
	
	default Negative decrement() {
		return negate().increment().negate();
	}
	
	default Negative twice() {
		return negate().twice().negate();
	}
	
	default Positive square() {
		return negate().square();
	}
	
	Positive negate();
	
	default Positive absolute() {
		return negate();
	}
	default Negative sign() {
		return MINUS_ONE;
	}
	
	default Integer add(Natural that) {
		return that.subtract(this.negate());
	}
	
	default Negative subtract(Natural that) {
		return negate().add(that).negate();
	}
	
	
	default Positive multiply(Negative that) {
		return negate().multiply(that.negate());
	}
	
	default Integer multiply(Natural that) {
		return negate().multiply(that).negate();
	}
	
	default Integer DIVIDE(Natural that) {
		return negate().DIVIDE(that).negate();
	}
	
	default Integer modulo(Natural that) {
		return negate().modulo(that).negate();
	}
	
	
	
	default Integer power(Natural that) {
		return that.accept(new Natural.Visitor<Integer>() {
			@Override
			public Integer handle(NaN that) {
				return that;
			}
			@Override
			public Integer handle(Double that) {
				return power(that.half()).square();
			}
			@Override
			public Integer handle(Increment that) {
				return multiply(power(that.decrement()));
			}
			@Override
			public Integer handle(Zero that) {
				return ONE;
			}
		});
	}
	
	static Negative of(Positive negative) {
		return new Negative() {
			public Positive negate() {
				return negative;
			}
			public String toString() {
				return "-"+negative;
			}
			public int hashCode() {
				return Objects.hash(negative);
			}
			public boolean equals(Object object) {
				if (object instanceof Negative) {
					Negative that = (Negative) object;
					return negative.equals(that.negate());
				}
				return false;
			}
		};
	}
	
	default boolean isBigger(Negative that) {
		return negate().isSmaler(that.negate());
	}
	default boolean isSmaler(Negative that) {
		return negate().isBigger(that.negate());
	}
	default boolean isEqual(Negative that) {
		return negate().isEqual(that.negate());
	}
	
	default boolean isBigger(Natural that) {
		return false;
	}
	default boolean isSmaler(Natural that) {
		return true;
	}
	default boolean isEqual(Natural that) {
		return false;
	}
	
	default Rational invert() {
		return Fraction.of(MINUS_ONE, negate());
	}
	
	default <T> T accept(Integer.Visitor<T> visitor) {
		return visitor.handle(this);
	}
	
	default int toInt() {
		return -negate().toInt();
	}
	
}
