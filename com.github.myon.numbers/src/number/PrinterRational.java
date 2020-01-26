package number;

import number.rational.Fraction;

public class PrinterRational extends PrinterNumber {

	public PrinterRational(char[] system, int precision) {
		super(system, precision);
	}

	@Override
	public String print(Fraction that) {
		return print(that.enuminator())+"/"+print(that.denuminator());
	}

}
