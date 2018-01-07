/**
 * 1.1.24 Give the sequence of values of p and q that are computed when Euclid’s algorithm
 * is used to compute the greatest common divisor of 105 and 24. Extend the code
 * given on page 4 to develop a program Euclid that takes two integers from the command
 * line and computes their greatest common divisor, printing out the two arguments for
 * each call on the recursive method. Use your program to compute the greatest common
 * divisor or 1111111 and 1234567.
 * 
 * @author "yao jiqian"
 *
 */
public class EuclidGCD {

	public static void main(String[] args) {

		if(args.length < 2) {
			System.out.print("Usage: EuclidGCD number1 number2");
			return;
		}
		long n1 = Long.parseLong(args[0]);
		long n2 = Long.parseLong(args[1]);
		long dividend = Math.max(n1, n2);
		long divisor = Math.min(n1, n2);
		long gcd = greatestCommonDivisor(dividend, divisor);
		
		System.out.printf("gcd:%d", gcd);

	}

	/**
	 *  Euclid’s algorithm, recurse.
	 * @param dividend
	 * @param divisor
	 * @return the greatest common divisor.
	 */
	public static long greatestCommonDivisor(long dividend, long divisor) {
		
		long mod = dividend % divisor;
		
		System.out.printf("Dividend:%d, Divisor:%d, GCD:%d\n", dividend, divisor, mod);
		
		if(mod == 0) return divisor;
		else return greatestCommonDivisor(divisor, mod);
	}
}
