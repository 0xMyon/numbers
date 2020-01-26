package number.real;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import number.Printer;
import number.rational.Rational;

public class Series implements Real {

	private Rational sum;
	
	// multiple sequences
	private List<Sequence> sequence = new LinkedList<>();
	
	public static Series of(Sequence... sequence) {
		return of(ZERO, sequence);
	}
	
	public static Series of(Rational initial, Sequence... sequence) {
		return new Series(initial, sequence);
	}
	
	private Series(Rational initial, Sequence... sequence) {
		this.sum = initial;
		for(int i = 0; i < sequence.length; i++) {
			this.sequence.add(sequence[i]);
		}
	}
	
	public String toString() {
		return Printer.toString(this);
	}
	
	@Override
	public Rational aprox() {
		return sum;
	}

	@Override
	public Rational error() {
		return sequence.get(0).current().aprox();
	}

	@Override
	public void next() {
		Sequence s = sequence.remove(0);
		sum = sum.add(s.current());
		insertOrdered(s.next());
	}

	private void insertOrdered(Sequence... next) {
		// TODO
		for(Sequence c : next)
			sequence.add(c);
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.handle(this);
	}

	@Override
	public Real add(Rational that) {
		return of(sum.add(that), sequence.toArray(new Sequence[sequence.size()]));
	}

	@Override
	public Real add(Series that) {
		return of(sum.add(that.sum), 
				Stream.concat(
						this.sequence.stream(), 
						that.sequence.stream()
						)
				.toArray(Sequence[]::new)
				);
	}

	@Override
	public Real multiply(Rational that) {
		return of(sum.multiply(that), 
				sequence.stream().map(that::multiply)
				.toArray(Sequence[]::new)
				);
	}

	@Override
	public Real multiply(Series that) {
		// (C1 + Rn) * (C2 + Sm)
		// => C1*C2 + C1*Sm + C2*Rn + Rn*Sm
		return of(this.sum.multiply(that.sum), 
				Stream.concat(
						Stream.concat(
								this.sequence.stream().map(that.sum::multiply),
								that.sequence.stream().map(this.sum::multiply)
						),
						this.sequence.stream().map(
								s -> that.sequence.stream().map(s::multiply)
						).reduce(Stream.of(), Stream::concat)
				).toArray(Sequence[]::new)
				);
	}

	@Override
	public Real absolute() {
		// TODO Auto-generated method stub
		return null;
	}

	
	// e^this
	public Series exp() {
		return Series.of(
			sequence.stream().map(Sequence::exp).reduce(
				Sequence.exp(sum), 
				Sequence::multiply
			)	
		);
	}

	@Override
	public Real ln() {
		// TODO Auto-generated method stub
		return sum.ln().add(Series.of());
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

	public String toStructure() {
		return "[["+sequence.stream().map(Object::toString).reduce((a,b)->a+", "+b).orElse("")+"]]";
	}
	
	//15.154262241479259
	
	
}
