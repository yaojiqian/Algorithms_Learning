
/**
 * computes the value of ln (N !)
 * @author "yao jiqian"
 * 
 * Exercise 1.1.20
 * Write a recursive static method that computes the value of ln (N !)
 */
public class NaturalLogarithm {

	public static void main(String[] args) {

		if(args.length < 1) {
			System.out.println("Usage: NaturalLogarigthm  number");
			return;
		}
		
		long N = Long.parseLong(args[0]);
		
		long fn = Factorial(N);
		System.out.printf("%d! = %d \n ln(%d!) = %f", N, fn, N, Math.log(fn));

	}

	/**
	 * Calculate the Factorial.
	 * @param N
	 * @return the N's factorial.
	 */
	public static long Factorial(long N) {
		
		if(N == 1 || N == 0) return 1;
		else return N * Factorial(N -1);
	}
}
