package number.complex;

import java.util.Objects;

import number.real.Real;

public interface Imaginary extends Complex {

	default <T> T accept(Visitor<T> visitor) {
		return visitor.handle(this);
	}
	
	@Override
	default Complex add(Real that) {
		return of(real().add(that), imaginary());
	}
	
	@Override
	default Complex multiply(Real that) {
		return of(
				this.real().multiply(that.real())
		   .add(this.imaginary().multiply(that.imaginary())), 
				this.imaginary().multiply(that.real())
		   .add(this.real().multiply(that.imaginary()))
				);
	}
	
	
	default Complex conjugate() {
		return of(real(), imaginary().negate());
	}
	
	default Real absolute() {
		return real().square().add(imaginary().square()).squareroot();
	}
	
	static Complex of(Real real, Real imaginary) {
		if (imaginary.isEqual(ZERO)) {
			return real;
		} else {
			return new Imaginary() {
				@Override
				public Real real() {
					return real;
				}
				@Override
				public Real imaginary() {
					return imaginary;
				}
				public boolean equals(Object other) {
					if (other instanceof Imaginary) {
						Imaginary that = (Imaginary) other;
						return Objects.equals(this.real(), that.real()) && Objects.equals(this.imaginary(), that.imaginary());
					}
					return false;
				}
				public int hashCode() {
					return Objects.hash(real, imaginary);
				}
				public String toString() {
					return (real().isEqual(ZERO) ? "" : real().toString()+"+")+imaginary().toString()+"i";
				}
			};
		}
	}
	
}
