package number.rational;

import java.util.Objects;

import number.Printer;
import number.integer.Integer;
import number.natural.Natural;
import number.natural.Positive;

public interface Fraction extends Rational {

	default Rational invert() {
		try {
		return of(denuminator().multiply(enuminator().sign()), 
				  (Positive) enuminator().absolute()
				);
		} catch (ClassCastException e) {
			return NONE;
		}
	}
	
	default <T> T accept(Rational.Visitor<T> visitor) {
		return visitor.handle(this);
	}
	
	@Override
	default Integer integer() {
		return enuminator().DIVIDE(denuminator());
	}
	@Override
	default Rational rational() {
		return subtract(integer());
	}
	
	static Rational of(Integer enuminator, Positive denuminator) {
		if (enuminator.isEqual((Natural)NONE) || denuminator.isEqual((Natural)NONE))
			return NONE;
		
		Natural ggt = enuminator.absolute().euklid(denuminator);
		if (ggt.isEqual(denuminator)) {
			return enuminator.DIVIDE(denuminator);
		} else {
			final Integer Enuminator = enuminator.DIVIDE(ggt);
			final Positive Denuminator = (Positive) denuminator.DIVIDE(ggt);
			return new Fraction() {
				@Override
				public Integer enuminator() {
					return Enuminator;
				}
				@Override
				public Positive denuminator() {
					return Denuminator;
				}
				public String toString() {
					return Printer.toString(this);
				}
				public int hashCode() {
					return Objects.hash(enuminator(), denuminator());
				}
				public boolean equals(Object object) {
					if (object instanceof Fraction) {
						Fraction that = (Fraction) object;
						return enuminator().equals(that.enuminator()) && denuminator().equals(that.denuminator());
					}
					return false;
				}
				
			};
		}
	}
	
	default Rational absolute() {
		return of(enuminator().absolute(), denuminator());
	}
	

	
	
}
