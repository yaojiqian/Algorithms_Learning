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

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private boolean[][] sites; // n-by-n grid
    private final WeightedQuickUnionUF unionSites;
    private final WeightedQuickUnionUF lastRowSites;
    private final int siteSize;
    // private final int lastSitePos;
    private int openSitesCount = 0;
    private boolean isPercolated = false;
    
    /**
     * create n-by-n grid, with all sites blocked
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException(
                            "the n should greater then 0.");
        }
        
        sites = new boolean[n][n];
        unionSites = new WeightedQuickUnionUF(n * n + 2);
        lastRowSites = new WeightedQuickUnionUF(n * n + 1);
        siteSize = n;
        // lastSitePos = siteSize * siteSize +1;
        if (sites.length == 0) {
            throw new java.lang.OutOfMemoryError();
        }     
    }
    
    private int getPosition(int row, int col) {
        return (row-1) * siteSize + col;
    }
    
    /**
     * open site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        if ((row <= 0 || row > siteSize) || (col <= 0 || col > siteSize)) {
            throw new java.lang.IllegalArgumentException(
                            "the row and col must be in [1..n]");
        }
        
        if (sites[row -1][col -1]) { // already opened.
            return;
        } else {
            sites[row -1][col -1] = true; // set to opened.
            openSitesCount++; // increase open sites count.
        }
        
        int siteInArrayPos = getPosition(row, col);
        // Try to connect top site.
        if (row == 1) {
            unionSites.union(0, siteInArrayPos);
        }
        
        // Try to connect top site.
        if (row == siteSize) {
            lastRowSites.union(0, siteInArrayPos);
        }
        
        int topRow = row -1;
        if ((topRow >= 1) && (sites[topRow -1][col -1])) {
            int topSiteInArrayPos = getPosition(topRow, col);
            unionSites.union(siteInArrayPos, topSiteInArrayPos);
            lastRowSites.union(siteInArrayPos, topSiteInArrayPos);
        }
 
        // Try to connect top site.
        int leftCol = col -1;
        if ((leftCol >= 1) && (sites[row -1][leftCol -1])) {
            int leftSiteInArrayPos =  getPosition(row, leftCol);
            unionSites.union(siteInArrayPos, leftSiteInArrayPos);
            lastRowSites.union(siteInArrayPos, leftSiteInArrayPos);
        }

        // Try to connect right site.
        int rightCol = col +1;
        if ((rightCol <= siteSize) && (sites[row -1][rightCol -1])) {
            int rightSiteInArrayPos =  getPosition(row, rightCol);
            unionSites.union(siteInArrayPos, rightSiteInArrayPos);
            lastRowSites.union(siteInArrayPos, rightSiteInArrayPos);
        }
        
        // Try to connect bottom site.
        int bottomRow = row +1;
        if ((bottomRow <= siteSize) && (sites[bottomRow -1][col -1])) {
            int bottomSiteInArrayPos =  getPosition(bottomRow, col);
            unionSites.union(siteInArrayPos, bottomSiteInArrayPos);
            lastRowSites.union(siteInArrayPos, bottomSiteInArrayPos);
        }
        
        if (lastRowSites.connected(siteInArrayPos, 0) 
                && this.isFull(row, col)) {
            isPercolated = true;
        }
    }
    
    /**
     * is site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        if ((row <= 0 || row > siteSize) || (col <= 0 || col > siteSize)) {
            throw new java.lang.IllegalArgumentException(
                            "the row and col must be in [1..n]");
        }
        /*
        if (sites[row -1][col -1] == true) {
            return true;
        }
        */
        return sites[row -1][col -1];
    }
    
    /**
     * is site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        if ((row <= 0 || row > siteSize) || (col <= 0 || col > siteSize)) {
            throw new java.lang.IllegalArgumentException(
                            "the row and col must be in [1..n]");
        }

        boolean retVal = false;
        if (sites[row -1][col -1]) {
            int siteInArrayPos = getPosition(row, col);
            retVal = unionSites.connected(0, siteInArrayPos);
        }
        return retVal;
    }
    
    /**
     * number of open sites
     */
    public int numberOfOpenSites() {
        return openSitesCount;
    }
    
    /**
     * does the system percolate?
     */
    public boolean percolates() {
        return isPercolated;
    }
    
    /**
     * test client
     */
    public static void main(String[] args) {
        
        int n = 50;
        Percolation per = new Percolation(n);
        while (!per.percolates()) {
            int newRow = StdRandom.uniform(n) +1;
            int newCol = StdRandom.uniform(n) +1;
            per.open(newRow, newCol);
        }
        System.out.print("percolates: ");
        System.out.println(per.numberOfOpenSites());
    }
}