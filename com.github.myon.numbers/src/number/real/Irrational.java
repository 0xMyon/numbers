package number.real;

public interface Irrational extends Real {

	// Irr := Rat + Err
	// Irr + Irr = (Rat+Rat) | (Err+Err)
	// Irr * Irr = Rat*Rat | Err*Rat + Rat*Err + Err*Err
	// Irr ^ Irr = Rat^Rat | Rat^Err + Err^Rat + Err^Err
	
	// e  --> *x /n
	// ln --> *x *-1 *(n-1)/n 
	// pi --> *-1 *n/n+2
	
	// ln(a*2^n) = ln(a) + n*ln(2)
	
	/*
	 * ln(x) := 
	 * if (x > 2) 
	 * 		return ln(x/2) + ln(2)
	 * if (x == 2)
	 * 		return "const"
	 * else
	 * 	    summe ...
	 * 
	 */
	
	// ln(2) = 3*ln(1+1/4) + ln(1+3/125)
	
}
