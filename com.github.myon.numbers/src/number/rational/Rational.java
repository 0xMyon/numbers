package number.rational;

import number.natural.Natural;
import number.natural.Positive;
import number.natural.Zero;
import number.nothing.NaN;
import number.real.Real;
import number.real.Sequence;
import number.real.Series;
import number.integer.Integer;
import number.integer.Negative;

public interface Rational extends Real {

	
	default Rational increment() {
		return add(ONE);
	}
	
	default Rational decrement() {
		return subtract(ONE);
	}
	
	default Rational twice() {
		return multiply(TWO);
	}
	
	default Rational half() {
		return divide(TWO);
	}
	
	default Rational square() {
		return multiply(this);
	}
	
	default Rational negate() {
		return multiply(MINUS_ONE);
	}
	
	default Rational power(Natural that) {
		return Fraction.of(
			enuminator().power(that), 
			denuminator().power(that)
		);
	}
	
	// e^this
	default Real exp() {
		return Series.of(Sequence.exp(this));
	}
	
	default Real ln() {
		if (isBigger(Rational.of(3,2)))
			return half().ln().add(LN_TWO);
		else if (!isBigger(ZERO))
			return NONE;
		else if (isSmaler(Rational.of(1, 2)))
			return twice().ln().subtract(LN_TWO);
		else
			return Series.of(Sequence.ln(this));
	}
	
	default Real sin() {
		return Series.of(Sequence.sin(this));
	}
	
	default Real cos() {
		return Series.of(Sequence.cos(this));
	}
	
		
	default Rational power(Integer that) {
		return that.accept(new Integer.Visitor<Rational>() {
			@Override
			public Rational handle(Negative that) {
				return power(that.negate()).invert();
			}
			@Override
			public Rational handle(Natural that) {
				return power(that);
			}
			@Override
			public NaN handle(NaN that) {
				return that;
			}
		});
	}
	
	Rational invert();
	
	Integer enuminator();
	Positive denuminator();
	
	/**
	 * @return the integer part
	 * 3/2 -> 1
	 * {@link #rational()} + {@link #integer()} = ID
	 */
	Integer integer();
	
	/**
	 * @return the pure rational part
	 * 3/2 -> 1/2
	 * {@link #rational()} + {@link #integer()} = ID
	 */
	Rational rational();
	
	default Real add(Series that) {
		return that.add(this);
	}
	
	default Rational add(Rational that) {
		return Fraction.of(
				this.enuminator().multiply(that.denuminator()).add(that.enuminator().multiply(this.denuminator())),
				this.denuminator().multiply(that.denuminator())
				);
	}
	
	default Rational subtract(Rational that) {
		return Fraction.of(
				this.enuminator().multiply(that.denuminator()).subtract(that.enuminator().multiply(this.denuminator())),
				this.denuminator().multiply(that.denuminator())
				);
	}
	
	default Real multiply(Series that) {
		return that.multiply(this);
	}
	
	default Rational multiply(Rational that) {
		return Fraction.of(
				enuminator().multiply(that.enuminator()),
				denuminator().multiply(that.denuminator())
				);
	}
	
	default Rational divide(Rational that) {
		return multiply(that.invert());
	}
	
	default boolean isBigger(Rational that) {
		return enuminator().multiply(that.denuminator()).isBigger(denuminator().multiply(that.enuminator()));
	}
	default boolean isSmaler(Rational that) {
		return enuminator().multiply(that.denuminator()).isSmaler(denuminator().multiply(that.enuminator()));
	}
	default boolean isEqual(Rational that) {
		return enuminator().multiply(that.denuminator()).isEqual(denuminator().multiply(that.enuminator()));
	}
	
	default Rational error() {
		return Zero.of();
	}
	default Rational aprox() {
		return this;
	}
	default void next() {}
	
	default <T> T accept(Real.Visitor<T> visitor) {
		return visitor.handle(this);
	}
	
	<T> T accept(Visitor<T> visitor);
	
	interface Visitor<T> {
		T handle(Integer that);
		T handle(Fraction that);
		T handle(NaN that);
	}
	
	
	static Rational of(long enuminator, long denuminator) {
		return Integer.of(enuminator).divide(Integer.of(denuminator));
	}

	Rational absolute();
	
}
