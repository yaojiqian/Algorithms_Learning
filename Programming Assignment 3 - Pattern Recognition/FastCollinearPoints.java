/******************************************************************************
 *  Name:    Yao Jiqian
 *  NetID:   JQ
 *  Precept: P03
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  A faster, sorting-based solution. Remarkably, it is possible to
 *          solve the problem much faster than the brute-force solution described
 *          above. Given a point p, the following method determines whether p
 *          participates in a set of 4 or more collinear points.
 *              Think of p as the origin.
 *              For each other point q, determine the slope it makes with p.
 *              Sort the points according to the slopes they makes with p.
 *              Check if any 3 (or more) adjacent points in the sorted order
 *                   have equal slopes with respect to p. If so, these points, 
 *                   together with p, are collinear.  
 ******************************************************************************/

// import java.util.Comparator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;
// import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
// import edu.princeton.cs.algs4.QuickUnionUF;

public class FastCollinearPoints {
    
    private int segmentsCount = 0;
    private LineSegment[] lineSegments = null;
    private final Point[] points;
    // private QuickUnionUF pointsUnion;
    // private boolean[][] segmentsUsed;
    // private final int sz = 0;
    
    private class PointSlope {
        int pointID;
        double slope;
        
        // public String toString() {
        //    return String.format("%d:%f", pointID, slope);
        // }
    }
    
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] pts) {
        if (pts == null) {
            throw new java.lang.IllegalArgumentException("The points is null.");
        }
        // if (points.length < 4) {
        //     throw new java.lang.IllegalArgumentException(
        //                            "The points count should not be little 4.");
        // }
        int n = pts.length;
        this.points = new Point[n];
        for (int i = 0; i < n; i++) {
            if (pts[i] == null)
                throw new java.lang.IllegalArgumentException();
            this.points[i] = pts[i];
        }
        // this.points = points;
        sort(this.points);
        
        buildSegments();
    }
    
    private PointSlope[] calculateSlope(int originID) {
        int n = points.length;
        PointSlope[] pss = new PointSlope[n];
        int count = 0;
        for (int i = originID + 1; i < n; i++) {
            PointSlope tps = new PointSlope();
            tps.pointID = i;
            tps.slope = points[originID].slopeTo(points[i]);
            pss[count] = tps;
            count++;
        }
        PointSlope[] retSlopes = new PointSlope[count];
        for (int i = 0; i < count; i++) {
            PointSlope tps = pss[i];
            for (int j = i + 1; j < count; j++) {
                if (Double.compare(pss[j].slope, tps.slope) < 0) {
                    PointSlope tt = tps;
                    tps = pss[j];
                    pss[j] = tt;
                } else if (Double.compare(pss[j].slope, tps.slope) == 0 && 
                     (points[pss[j].pointID].compareTo(points[tps.pointID])) < 0) {
                    PointSlope tt = tps;
                    tps = pss[j];
                    pss[j] = tt;
                } 
            }
            pss[i] = tps;
            retSlopes[i] = tps;
        }
        return retSlopes;
    }
    
    private void addNewSegment(int[] ids, int count, boolean[][] used) {
        LineSegment newSeg = new LineSegment(points[ids[0]], 
                                             points[ids[count - 1]]);
        
        segmentsCount++;
        if (lineSegments == null) {
            lineSegments = new LineSegment[] { newSeg };
        } else {
            LineSegment[] tempSegs = new LineSegment[lineSegments.length + 1];
            int idx = 0;
            for (LineSegment ls : lineSegments) {
                tempSegs[idx] = ls;
                idx++;
            }
            tempSegs[lineSegments.length] = newSeg;
            lineSegments = tempSegs;
        }
        
        for (int i = 0; i < count -1; i++) {
            for (int j = i+1; j < count; j++) {
                int minSub = Math.min(ids[i], ids[j]);
                int maxSub = Math.max(ids[i], ids[j]);
                used[minSub][maxSub] = true;
            }
        }
        
    }
    
    private void buildSegments() {
        int n = points.length;
        boolean[][] segmentsUsed = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            PointSlope[] sortedSlopes = calculateSlope(i);
            int slopeStart = 0;
            int countInSameSlope;
            for (int j = 1; j < sortedSlopes.length; j++) {
                if (Double.compare(sortedSlopes[slopeStart].slope, 
                                   sortedSlopes[j].slope) != 0) {
                    countInSameSlope = j - slopeStart;
                    if (countInSameSlope >= 3) {
                        int[] sameSlopePoints = new int[countInSameSlope +1];
                        sameSlopePoints[0] = i;
                        for (int k = 0; k < countInSameSlope; k++) {
                            sameSlopePoints[k +1] = 
                                sortedSlopes[k + slopeStart].pointID;
                        }
                        // new a segments; & mard used
                        int minSub = Math.min(i, sameSlopePoints[1]);
                        int maxSub = Math.max(i, sameSlopePoints[1]);
                        // boolean shouldAddSeg = false;
                        if (!segmentsUsed[minSub][maxSub]) { 
                            addNewSegment(sameSlopePoints, 
                                          countInSameSlope +1, segmentsUsed);
                        }
                    }
                    slopeStart = j;
                }
            }
            countInSameSlope = sortedSlopes.length - slopeStart;
            if (countInSameSlope >= 3) {
                int[] sameSlopePoints = new int[countInSameSlope +1];
                sameSlopePoints[0] = i;
                for (int k = 0; k < countInSameSlope; k++) {
                    sameSlopePoints[k +1] = sortedSlopes[k + slopeStart].pointID;
                }
                // new a segments; & mard used
                int minSub = Math.min(i, sameSlopePoints[1]);
                int maxSub = Math.max(i, sameSlopePoints[1]);
                // boolean shouldAddSeg = false;
                if (!segmentsUsed[minSub][maxSub]) { 
                    addNewSegment(sameSlopePoints, countInSameSlope +1, segmentsUsed);
                }
            }
        }
    }
    
    private static int partition(Point[] a, int lo, int hi) {
        int i = lo, j = hi +1;
        while (true) {
            // while (a[++i].compareTo(a[lo]) < 0)
            //    if (i == hi) break;
            for (i++; i < hi; i++) {
                if (a[i].compareTo(a[lo]) > 0)
                    break;
                else if (a[i].compareTo(a[lo]) == 0)
                    throw new java.lang.IllegalArgumentException();
            }
            
           //  while (a[lo].compareTo(a[--j]) < 0)
           //     if (j == lo) break;
            
            for (j--; j > lo; j--) {
                if (a[lo].compareTo(a[j]) > 0)
                    break;
                else if (a[lo].compareTo(a[j]) == 0)
                    throw new java.lang.IllegalArgumentException();
            }
            
            if (i >= j) break;
            Point temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
        
        Point temp = a[lo];
        a[lo] = a[j];
        a[j] = temp;
        
        return j;
    }
    
    private static void sort(Point[] a)
    {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }
    
    private static void sort(Point[] a, int lo, int hi)
    {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }
    
    // the number of line segments
    public int numberOfSegments() {
        return segmentsCount;
    }
    // the line segments
    public LineSegment[] segments() {
        LineSegment[] temp = lineSegments;
        if (temp == null) {
            temp = new LineSegment[0];
            return temp;
        }
        return temp;
    }
    
    
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}