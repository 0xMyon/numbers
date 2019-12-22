package number;

import number.rational.Rational;
import number.integer.Integer;
import number.natural.Natural;

public interface Printer {

	final static char[] BIN = new char[] {'0', '1'};
	final static char[] OCT = new char[] {'0', '1', '2', '3', '4', '5', '6', '7'};
	final static char[] DEC = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	final static char[] HEX = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	
	final static char[] DEF = DEC;
	
	
	static String print(Rational x, int precision) {
		return print(x, DEF, precision);
	}
	
	static String print(Rational x, char[] rep, int precision) {
		String result = print(x.integer(), rep) + '.';
		Rational r = x.absolute();
		Natural base = Natural.of(rep.length);
		for (int i = 0; i < precision; i++) {
			r = r.rational();
			r = r.multiply(base);
			result += rep[r.integer().toInt()];
		}
		return result;
	}
	
	static String print(final Integer i) {
		return print(i, DEF);
	}
	
	static String print(final Integer i, final char[] rep) {
		if (i.isEqual(Number.ZERO)) return "0";
		Natural base = Natural.of(rep.length);
		String result = "";
		Natural n = i.absolute();
		while(n.isBigger(Number.ZERO)) {
			Natural mod = n.modulo(base);		
			result = rep[mod.toInt()] + result;
			n = n.DIVIDE(base);
		}
		return (i.isSmaler(Number.ZERO) ? "-" : "") + result;
	}
	
	
}
