package number;

import static org.junit.jupiter.api.Assertions.*;

import number.integer.Integer;
import number.natural.Natural;
import number.nothing.NaN;
import number.rational.Rational;
import number.real.Real;

class Test {

	@org.junit.jupiter.api.Test
	void test() {
		
		//System.out.println(Math.pow(Math.E, Math.E));
		
		assertNotEquals(Integer.of(Long.MAX_VALUE+1), Integer.of(Long.MAX_VALUE).increment());
		
		assertNotEquals(Number.NONE, Number.ZERO);
		assertNotEquals(Number.ZERO, Number.NONE);
		
		assertEquals(Number.ZERO, Number.ZERO.negate());
		assertEquals(Number.NONE, Number.ZERO.power(Number.ZERO));
		
		//System.out.println(Number.ONE.exp());
		
		for(long a=-10; a < 20; a++) {
			
			assertEquals(a, Integer.of(a).toInt());
			assertEquals(String.valueOf(a), new PrinterFloat().print(Integer.of(a)));
			
			
			assertEquals(Integer.of(a+1), Integer.of(a).increment());
			assertEquals(Integer.of(a-1), Integer.of(a).decrement());
			assertEquals(Integer.of(a<<1), Integer.of(a).twice());
			
			assertEquals(Integer.of(a), Integer.of(a).decrement().increment());
			assertEquals(Integer.of(a), Integer.of(a).increment().decrement());
			assertEquals(Integer.of(a), Integer.of(a).twice().half());
			assertEquals(Integer.of(a), Integer.of(a).half().twice());
			
			assertEquals(Integer.of(a), Integer.of(a).negate().negate());
			if (a != 0)
			assertEquals(Integer.of(a), Integer.of(a).invert().invert());
			
			
			assertEquals(Number.NONE, Integer.of(a).add((Natural)Number.NONE));
			assertEquals(Number.NONE, Integer.of(a).multiply((Natural)Number.NONE));
			assertEquals(Number.NONE, Integer.of(a).power((Natural)Number.NONE));
			assertEquals(Number.NONE, Integer.of(a).subtract((Natural)Number.NONE));
			assertEquals(Number.NONE, Integer.of(a).divide((Natural)Number.NONE));
			
			assertEquals(Number.NONE, Number.NONE.add(Integer.of(a)));
			assertEquals(Number.NONE, Number.NONE.multiply(Integer.of(a)));
			assertEquals(Number.NONE, Number.NONE.power(Integer.of(a)));
			assertEquals(Number.NONE, Number.NONE.subtract(Integer.of(a)));
			assertEquals(Number.NONE, Number.NONE.divide(Integer.of(a)));
			
			assertEquals(Number.NONE, Integer.of(a).divide(Number.ZERO));
			
			for(long b=-10; b < 10; b++) {
				
				//System.out.println("a="+a+" b="+b);
				
				assertEquals(Integer.of(a+b), Integer.of(a).add(Integer.of(b)));
				assertEquals(Integer.of(a*b), Integer.of(a).multiply(Integer.of(b)));
				
				
				assertEquals(Integer.of(a), Integer.of(a).add((Integer.of(b))).subtract(Integer.of(b)) );
				assertEquals(Integer.of(a), Integer.of(a).subtract((Integer.of(b))).add(Integer.of(b)) );
				if (b != 0) {
					assertEquals(Integer.of(a), Integer.of(a).multiply((Integer.of(b))).DIVIDE(Integer.of(b)) );
					assertEquals(Integer.of(a), Integer.of(a).divide((Integer.of(b))).multiply(Integer.of(b)) );
				}
				
				if (b >= 0 && a != 0)
					assertEquals(Integer.of((long)Math.pow(a, b)), Integer.of(a).power(Integer.of(b)));
				
				assertEquals(Integer.of(a-b), Integer.of(a).subtract(Integer.of(b)));
				
				if (0 != b) {
					assertEquals(Integer.of(a/b), Integer.of(a).DIVIDE(Integer.of(b)));
					
					if (b > 0 && a > 0)
						assertEquals(Integer.of(a%b), Natural.of(a).modulo(Natural.of(b)));
				
					assertEquals(Rational.of(a,b), Integer.of(a).divide(Integer.of(b)));
					
					if (0 != a) {
						assertEquals(Number.ONE, Rational.of(a,b).multiply(Rational.of(a,b).invert()));
						assertEquals(Rational.of(a,b).invert(), Rational.of(a,b).power(Number.MINUS_ONE));
						assertEquals(Number.ONE, Rational.of(a,b).invert().multiply(Rational.of(a,b)));
					}
				
					assertEquals(Rational.of(a,b).twice(), Rational.of(a,b).add(Rational.of(a,b)));
					assertEquals(Number.ZERO, Rational.of(a,b).subtract(Rational.of(a,b)));
				
					assertEquals(Rational.of(a,b), Rational.of(a,b).negate().negate());
					assertEquals(Rational.of(a,b), Rational.of(a,b).integer().add(Rational.of(a,b).rational()));
					
					
					
				}
				
				
				assertEquals(a < b, Integer.of(a).isSmaler(Integer.of(b)));
				assertEquals(a == b, Integer.of(a).isEqual(Integer.of(b)));
				assertEquals(a > b, Integer.of(a).isBigger(Integer.of(b)));
				
				
				
				
			}	
		}
		
		
		
		Real e = Number.E;
		Real ln2 = Number.LN_TWO;
		//Series ln2 = Series.of(Sequence.ln(Rational.of(999, 1000)));
		//Series e_e = e.exp();
		
		for(int i = 0; i < 50; i++) {
			//System.out.println(e);
			System.out.println(ln2);
			
			//System.out.println(e_e);
			
			e.next();
			ln2.next();
			//e_e.next();
			//ln2.next();
		}
		
	}
	
	
	private NaN x() {
		return new NaN();
	}
	
	@org.junit.jupiter.api.Test
	public void test2() {
		assertEquals(new NaN(), x());
	}
	

}
