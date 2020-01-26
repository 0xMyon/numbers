package number;

import number.integer.Negative;
import number.natural.Double;
import number.natural.Increment;
import number.rational.Fraction;
import number.real.Series;

public class PrinterStructure extends Printer {


	@Override
	public String print(Series that) {
		return that.aprox().toString()+"+"+that.toStructure();
	}

	@Override
	public String print(Fraction that) {
		return print(that.enuminator())+"/"+print(that.denuminator());
	}

	@Override
	public String print(Negative that) {
		return "-"+print(that.negate());
	}

	@Override
	public String print(Increment that) {
		return print(that.decrement())+"+1";
	}

	@Override
	public String print(Double that) {
		return "2*("+print(that.half())+")";
	}

}
