package number;

import number.integer.Negative;
import number.natural.Double;
import number.natural.Increment;
import number.natural.Natural;
import number.natural.Positive;
import number.real.Series;

public abstract class PrinterNumber extends Printer {

	static final char[] BIN = new char[] {'0', '1'};
	static final char[] OCT = new char[] {'0', '1', '2', '3', '4', '5', '6', '7'};
	protected static final char[] DEC = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	static final char[] HEX = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	protected final char[] system;
	protected final int precision;
	protected final Natural base;

	public PrinterNumber(char[] system, int precision) {
		this.system = system;
		this.precision = precision;
		base = Natural.of(system.length);
	}


	@Override
	public String print(Series that) {
		return cut(print(that.aprox()));
	}

	@Override
	public String print(Negative that) {
		return cut("-"+print(that.negate()));
	}

	public String print(Positive that) {
		return cut(print(that.DIVIDE(base)) + system[that.modulo(base).toInt()]);
	}

	@Override
	public String print(Double that) {
		return cut(print((Positive)that));
	}

	@Override
	public String print(Increment that) {
		return cut(print((Positive)that));
	}

	public String cut(String s) {
		if (s.length() > 1 && s.charAt(0) == '0') return cut(s.substring(1));
		//if (s.length() > 1 && s.charAt(0) == '-' && s.charAt(1) == '0') return cut('-'+s.substring(2));
		//if (s.charAt(s.length()-1) == '0') cut(s.substring(0,s.length()-1));
		return s;
	}

}