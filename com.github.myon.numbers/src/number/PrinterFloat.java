package number;

import number.rational.Fraction;
import number.rational.Rational;

public class PrinterFloat extends PrinterNumber {

	PrinterFloat() {
		this(DEC, 10);
	}
	
	PrinterFloat(char[] system, int precision) {
		super(system, precision);
	}
	
	@Override
	public String print(Fraction that) {
		String result = print(that.integer()) + ".";
		Rational r = that.absolute();
		for (int i = 0; i < precision; i++) {
			r = r.rational();
			r = r.multiply(base);
			result += system[r.integer().toInt()];
		}
		return cut(result);
	}
	
}
