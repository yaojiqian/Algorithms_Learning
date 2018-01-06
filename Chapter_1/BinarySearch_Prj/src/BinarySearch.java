/**
 * 
 */

/**
 * @author yao jiqian
 *
 */

import java.util.Arrays;

public class BinarySearch {

	/**
	 * Find the key in the array.
	 * 
	 * @param key, key to find in the a.
	 * @param a, a sorted array.
	 * @return -1: not find; otherwise: the index of the key in a;
	 */
	public static int rank(int key, int[] a) {
		// Array must be sorted.
		int lo = 0;
		int hi = a.length -1;
		
		while(lo <= hi) {
			// Key is in a[lo..hi] or not present.
			int mid = lo + (hi - lo)/2;
			if(key < a[mid]) hi = mid -1;
			else if (key > a[mid]) lo = mid +1;
			else return mid;
		}
		return -1;
	}
	
	public static int rank_density(int key, int[] a) {
		
		int lo = 0;
		int hi = a.length -1;
		
		while(lo <= hi) {
			double density = ((double)((a[hi] - a[lo])) / (hi -lo +1));
			int mid = lo + (int)((key - a[lo]) / density);
			mid = mid > hi ? hi : mid;
			mid = mid < lo ? lo : mid;
			
			if(key < a[mid]) hi = mid -1;
			else if (key > a[mid]) lo = mid +1;
			else return mid;
		}
		return -1;
	}
	
	/**
	 * 1.1.22 Write a version of BinarySearch that uses the recursive rank() given on page
	 * 25 and traces the method calls. Each time the recursive method is called, print the argument
	 * values lo and hi, indented by the depth of the recursion. Hint: Add an argument
	 * to the recursive method that keeps track of the depth.
	 * 
	 * @param key, key to find in the a.
	 * @param a, a sorted array.
	 * @return -1: not find; otherwise: the index of the key in a;
	 */
	public static int rank_recurse(int key, int[] a) {
		int lo =0;
		int hi =a.length -1;
		
		if(lo > hi) return -1;
		int mid = lo + (hi - lo) / 2;
		if(a[mid] == key) return mid;
		int[] ac = (a[mid] < key) ? Arrays.copyOfRange(a, mid +1, hi +1) : Arrays.copyOfRange(a, lo, mid);
		return rank_recurse(key, ac);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] whitelist = (new In(args[0])).readAllInts();
		Arrays.sort(whitelist);
		while (!StdIn.isEmpty()) { // Read key, print if not in whitelist.
			int key = StdIn.readInt();
			//if (rank(key, whitelist) < 0)
			//if(rank_recurse(key, whitelist) < 0)
			if(rank_density(key, whitelist) < 0)
				StdOut.println(key);
		}
	}

}
