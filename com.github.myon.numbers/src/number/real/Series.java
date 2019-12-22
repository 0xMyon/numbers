package number.real;

import number.Number;
import number.rational.Rational;

public class Series implements Real {

	private Rational sum;
	
	private Sequence<Rational> sequence;
	
	public static Series of(Sequence<Rational> sequence) {
		return of(Number.ZERO, sequence);
	}
	
	public static Series of(Rational sum, Sequence<Rational> sequence) {
		return new Series(sum, sequence);
	}
	
	private Series(Rational sum, Sequence<Rational> sequence) {
		this.sum = sum;
		this.sequence = sequence;
	}
	
	
	@Override
	public Rational aprox() {
		return sum;
	}

	@Override
	public Rational error() {
		return sequence.current();
	}

	@Override
	public void next() {
		sum = sum.add(sequence.current());
		sequence = sequence.next();
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.handle(this);
	}

	@Override
	public Real add(Rational that) {
		return of(sum.add(that), sequence);
	}

	@Override
	public Real add(Series that) {
		return of(sum.add(that.sum), sequence.add(that.sequence));
	}

	@Override
	public Real multiply(Rational that) {
		return of(sum.multiply(that), sequence.multiply(that, Rational::multiply));
	}

	@Override
	public Real multiply(Series that) {
		// (C1 + Rn) * (C2 + Sm)
		// => C1*C2 + C1*Sm + C2*Rn + Rn*Sm
		return of(sum.multiply(that.sum), 
				sequence.multiply(that.sum, Rational::multiply)
				.add(that.sequence.multiply(sum, Rational::multiply))
				.add(sequence.multiply(that.sequence)));
	}

	@Override
	public Real ln() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Real exp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Real absolute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Real sin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Real cos() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
