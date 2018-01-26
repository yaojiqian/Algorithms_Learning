/******************************************************************************
 *  Name:    Yao Jiqian
 *  NetID:   JQ
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  Model an n-by-n percolation system using the union-find
 *                data structure.
 ******************************************************************************/

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] percolatedCounts;
    private final double theMean;
    private final double theStdDev;
    
    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be greater then 0");
        }
        percolatedCounts = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation per = new Percolation(n);
            while (!per.percolates()) {
                int newRow = StdRandom.uniform(n) +1;
                int newCol = StdRandom.uniform(n) +1;
                per.open(newRow, newCol);
            }
            percolatedCounts[i] = ((double) per.numberOfOpenSites()) / n /n;
        }
        theMean = StdStats.mean(percolatedCounts);
        theStdDev = StdStats.stddev(percolatedCounts);
    }
    
    // sample mean of percolation threshold
    public double mean() {
        return theMean;
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        return theStdDev;
    }
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(percolatedCounts.length);
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(percolatedCounts.length);
    }
    
    // test client (described below)
    public static void main(String[] args) {
        if (args.length < 2) {
            StdOut.println("Usage: PercolationStats n trials");
            return;
        }
        int siteSize = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        
        StdOut.println("Starting to percolation stats....");
        Stopwatch sp = new Stopwatch();
        PercolationStats perStats = new PercolationStats(siteSize, trials);
        StdOut.printf("Elapsed Time: %f\n", sp.elapsedTime());
        StdOut.printf("mean \t\t\t\t= %f\n", perStats.mean());
        StdOut.printf("stddev \t\t\t\t= %f\n", perStats.stddev());
        StdOut.printf("95%% confidence interval \t= [%f, %f]\n", 
                      perStats.confidenceLo(), perStats.confidenceHi());        
    }
}