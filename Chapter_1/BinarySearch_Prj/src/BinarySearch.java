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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] whitelist = (new In(args[0])).readAllInts();
		Arrays.sort(whitelist);
		while (!StdIn.isEmpty()) { // Read key, print if not in whitelist.
			int key = StdIn.readInt();
			if (rank(key, whitelist) < 0)
				StdOut.println(key);
		}
	}

}
