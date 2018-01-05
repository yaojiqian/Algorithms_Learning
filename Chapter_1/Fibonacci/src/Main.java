/*
 * Main.java
 * 
 * This project to calculate the Fibonacci;
 * Will use two ways: the recursing and looping
 * Then compare the performance of these ways.
 */


//the Main class defines the program entry.
public class Main {
	
	public static void main(String[] args) {
		
		if(args.length < 1) {
			System.out.println("Usage: Main parameter\n");
			return;
		}
		final long N = Long.parseLong(args[0]);
		
		System.out.printf("recurse start...\n");
		long recurse_time = System.nanoTime();
		long result = Fibonacci(N);
		recurse_time = System.nanoTime() - recurse_time;
		//System.out.printf("recurse   end:%d\n", System.nanoTime());
		System.out.printf("recurse result:%d\n", result);
		
		long[] ar = new long[(int)N +1];
		System.out.printf("looping start...\n");
		long looping_time = System.nanoTime();
		//looping to calculate Fibonacci...
		for(int i =0; i < (N+1); i++) {
			if(i == 0) {
				ar[i] = 0;
			} else if(i == 1) {
				ar[i] = 1;
			} else {
				ar[i] = ar[i-1] + ar[i-2];
			}
			
		}
		//System.out.printf("looping   end:%d\n", System.nanoTime());
		looping_time = System.nanoTime() - looping_time;
		System.out.printf("looping result:%d\n", ar[(int)N]);
		
		System.out.printf("Time consumed: \n%20d\n%20d\n", recurse_time, looping_time);
	}
	
	//recurse function to calculate Fibonacci.
	public static long Fibonacci(long N) {
		if( N == 0) return 0;
		if( N == 1) return 1;
		return Fibonacci(N-1) + Fibonacci(N-2);
	}
}
