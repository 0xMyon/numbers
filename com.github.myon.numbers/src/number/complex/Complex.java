package number.complex;

import number.Number;
import number.nothing.NaN;
import number.real.Real;


public interface Complex extends Number {

	Real absolute();
	
	Complex conjugate();
	
	default Complex increment() {
		return add(ONE);
	}
	default Complex decrement() {
		return subtract(ONE);
	}
	
	default Complex twice() {
		return multiply(TWO);
	}
	default Complex half() {
		return divide(TWO);
	}
	
	default Complex square() {
		return power(TWO);
	}
	default Complex squareroot() {
		return power(HALF);
	}
	
	default Complex negate() {
		return multiply(MINUS_ONE);
	}
	
	default Complex invert() {
		return power(MINUS_ONE);
	}
	
	default Complex exp() {
		return Imaginary.of(imaginary().sin(), imaginary().cos()).multiply(real().exp());
	}
	
	
	default Complex power(Complex that) {
		// TODO 
		return null;
	}
	
	Complex add(Real that);
	default Complex add(Imaginary that) {
		// TODO
		return null;
	}
	
	// A+a + B+b
	default Complex add(Complex that) {
		return that.accept(new Visitor<Complex>() {
			@Override
			public Complex handle(Real that) {
				return add(that);
				}
			@Override
			public Complex handle(Imaginary that) {
				return add(that);
			}
			@Override
			public NaN handle(NaN that) {
				return that;
			}
		});
	}
	
	Complex multiply(Real that);
	
	default Complex multiply(Imaginary that) {
		return null;
	}
	
	// A*B + A*b + a*B + (a*b -> 0) 
	default Complex multiply(Complex that) {
		return that.accept(new Visitor<Complex>() {
			@Override
			public Complex handle(Real that) {
				return multiply(that);
				}
			@Override
			public Complex handle(Imaginary that) {
				return multiply(that);
			}
			@Override
			public NaN handle(NaN that) {
				return that;
			}
		});
	}
	
	default Complex divide(Complex that) {
		return multiply(that.invert());
	}
	
	default Complex subtract(Complex that) {
		return add(that.negate());
	}
	
	Real real();
	Real imaginary();
	
	<T> T accept(Visitor<T> visitor);
	
	interface Visitor<T> {
		T handle(Imaginary that);
		T handle(Real that);
		T handle(NaN that);
	}
	
}
