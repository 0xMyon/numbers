package number;

import number.complex.Complex;
import number.complex.Imaginary;
import number.integer.Integer;
import number.integer.Negative;
import number.natural.Double;
import number.natural.Increment;
import number.natural.Natural;
import number.natural.Zero;
import number.nothing.NaN;
import number.rational.Fraction;
import number.rational.Rational;
import number.real.Real;
import number.real.Series;

public abstract class Printer {

	public static Printer instance = null;
	
	public static String toString(Complex that) {
		if (instance == null) {
			instance = new PrinterStructure();
		}
		return instance.print(that);
	}
	
	
	public abstract String print(Series that);
	public abstract String print(Fraction that);
	public abstract String print(Negative that);
	public abstract String print(Increment that);
	public abstract String print(Double that);

	public String print(NaN that) {
		return "NaN";
	}
	
	public String print(Zero that) {
		return "0";
	}

	
	public String print(Complex that) {
		return that.accept(new Complex.Visitor<String>() {
			@Override
			public String handle(Real that) {
				return print(that);
			}
			@Override
			public String handle(Imaginary that) {
				return print(that);
			}
			@Override
			public String handle(NaN that) {
				return print(that);
			}
		});
	}
	
	public String print(Real that) {
		return that.accept(new Real.Visitor<String>() {
			@Override
			public String handle(Series that) {
				return print(that);
			}
			@Override
			public String handle(Rational that) {
				return print(that);
			}
			@Override
			public String handle(NaN that) {
				return print(that);
			}
		});
	}
	
	public String print(Rational that) {
		return that.accept(new Rational.Visitor<String>() {
			@Override
			public String handle(Integer that) {
				return print(that);
			}
			@Override
			public String handle(Fraction that) {
				return print(that);
			}
			@Override
			public String handle(NaN that) {
				return print(that);
			}
		});
	}
	
	public String print(Integer that) {
		return that.accept(new Integer.Visitor<String>() {
			@Override
			public String handle(Negative that) {
				return print(that);
			}
			@Override
			public String handle(Natural that) {
				return print(that);
			}
			@Override
			public String handle(NaN that) {
				return print(that);
			}
		});
	}
	
	public String print(Natural that) {
		return that.accept(new Natural.Visitor<String>() {
			@Override
			public String handle(Double that) {
				return print(that);
			}
			@Override
			public String handle(Increment that) {
				return print(that);
			}
			@Override
			public String handle(Zero that) {
				return print(that);
			}
			@Override
			public String handle(NaN that) {
				return print(that);
			}
		});
	}
	
	
}
