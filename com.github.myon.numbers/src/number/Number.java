package number;

import number.complex.Complex;
import number.complex.Imaginary;
import number.integer.Negative;
import number.natural.Double;
import number.natural.Increment;
import number.natural.Zero;
import number.nothing.NaN;
import number.rational.Rational;
import number.real.Real;

public interface Number {

	static final Zero ZERO = Zero.of();
	static final Increment ONE = ZERO.increment();
	static final Double TWO = ONE.twice();
	static final Increment THREE = TWO.increment();
	
	static final NaN NONE = NaN.of();
	
	static final Negative MINUS_ONE = ONE.negate();
	
	static final Rational HALF = TWO.invert();
	static final Rational THREE_HALFS = ONE.add(HALF);
	
	
	static final Real E = ONE.exp();
	
	static final Rational PRESCISION = Rational.of(1, 1000000);
	
	// 2 = 1.25^3*1.024
	static final Real LN_TWO = THREE.multiply(Rational.of(5, 4).ln()).add(Rational.of(125+3, 125).ln());

	static final Complex I = Imaginary.of(ZERO, ONE);
	
}
