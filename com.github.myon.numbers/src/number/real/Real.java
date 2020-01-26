package number.real;

import number.complex.Complex;
import number.nothing.NaN;
import number.rational.Rational;

public interface Real extends Complex {

	default Real increment() {
		return add(ONE);
	}
	default Real decrement() {
		return subtract(ONE);
	}
	
	default Real twice() {
		return multiply(TWO);
	}
	default Real half() {
		return divide(TWO);
	}
	
	default Real square() {
		return multiply(this);
	}
	default Real squareroot() {
		return power(HALF);
	}
	
	default Real negate() {
		return multiply(MINUS_ONE);
	}
	
	default Real invert() {
		return power(MINUS_ONE);
	}
	
	// TODO error must be smaler than the difference!
	default boolean isBigger(Real that) {
		return aprox().isBigger(that.aprox());
	}
	default boolean isSmaler(Real that) {
		return aprox().isSmaler(that.aprox());
	}
	default boolean isEqual(Real that) {
		return aprox().isEqual(that.aprox());
	}
	
	Real add(Rational that);
	Real add(Series that);
	
	// A+a + B+b
	default Real add(Real that) {
		return that.accept(new Visitor<Real>() {
			@Override
			public Real handle(Series that) {
				return add(that);
				}
			@Override
			public Real handle(Rational that) {
				return add(that);
			}
			@Override
			public NaN handle(NaN that) {
				return that;
			}
		});
	}
	
	Real multiply(Rational that);
	Real multiply(Series that);
	
	default Real multiply(Real that) {
		return that.accept(new Visitor<Real>() {
			@Override
			public Real handle(Series that) {
				return multiply(that);
				}
			@Override
			public Real handle(Rational that) {
				return multiply(that);
			}
			@Override
			public NaN handle(NaN that) {
				return that;
			}
		});
	}
	
	default Real divide(Real that) {
		return multiply(that.invert());
	}
	
	default Real subtract(Real that) {
		return add(that.negate());
	}
	
	default Rational aprox(Rational error) {
		while (error().isBigger(error))
			next();
		return aprox();
	}
	
	Rational aprox();
	Rational error();
	

	void next();
	
	Series exp();
	
	Real ln();
	
	Real sin();
	Real cos();
	
	
		
	/*
	 * (0)root(x) = 0
	 * (0)root(0) = NaN
	 * (-a)root(2n+1) = -((a)root(2n+1))
	 * (-a)root(2n) = (i)root(n) * (a)root(2n)
	 */
	default Real power(Real that) {
		if (isBigger(ZERO))
			return that.multiply(this.ln()).exp();
		else if (isEqual(ZERO))
			return ONE;
		else
			return power(that.negate()).invert();
	}
	
	
	default <T> T accept(Complex.Visitor<T> visitor) {
		return visitor.handle(this);
	}
	
	<T> T accept(Visitor<T> visitor);
	
	interface Visitor<T> {
		T handle(Series that);
		T handle(Rational that);
		T handle(NaN that);
	}
	
	
	default Real real() {
		return this;
	}
	default Real imaginary() {
		return ZERO;
	}
	
	default Real conjugate() {
		return this;
	}
	
	
}
