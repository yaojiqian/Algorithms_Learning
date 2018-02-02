/******************************************************************************
 *  Name:    Yao Jiqian
 *  NetID:   JQ
 *  Precept: P02
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  takes an integer k as a command-line argument; reads in a 
 *                sequence of strings from standard input using StdIn.readString(); 
 *                and prints exactly k of them, uniformly at random. 
 *                Print each item from the sequence at most once. 
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    
    public static void main(String[] args) {
        
        if (args.length != 1) {
            StdOut.println("Usage: Permutation k");
            return;
        }
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        try {
            // String[] as = StdIn.readAllStrings();
            while (!StdIn.isEmpty()) {
                String s = StdIn.readString();
                q.enqueue(s);
            }
        } catch (java.util.NoSuchElementException ex) {
            // String msg;
            // msg = "end of readString()";
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(q.dequeue());
        }
    }
}