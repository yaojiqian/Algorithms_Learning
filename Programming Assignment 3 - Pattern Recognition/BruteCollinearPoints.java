/******************************************************************************
 *  Name:    Yao Jiqian
 *  NetID:   JQ
 *  Precept: P03
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description: examines 4 points at a time and checks whether they all lie 
 *      on the same line segment, returning all such line segments. To check
 *      whether the 4 points p, q, r, and s are collinear, check whether the
 *      three slopes between p and q, between p and r, and between p and s are 
 *      all equal. 
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
// import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.QuickUnionUF;

public class BruteCollinearPoints {
    
    private int segmentsCount = 0;
    private LineSegment[] lineSegments = null;
    
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("The points is null.");
        }
        // if (points.length < 4) {
        //     throw new java.lang.IllegalArgumentException(
        //                          "The points count should not be little 4.");
        // }
        // Sort the points.
        int n = points.length;
        for (int i = 0; i < n; i++) {
            Point p = points[i];
            if (p == null) {
                throw new java.lang.IllegalArgumentException(
                                                     "One of the point is null.");
            }
            for (int j = i +1; j < n; j++) {
                if (points[j] == null) {
                    throw new java.lang.IllegalArgumentException(
                                                     "One of the point is null.");
                }
                int pc = p.compareTo(points[j]);
                if (pc > 0) {
                    Point tp = p;
                    p = points[j];
                    points[j] = tp;
                } else if (pc == 0) {
                    throw new java.lang.IllegalArgumentException("have same point.");
                }
            }
            points[i] = p;
        }
        
        buildSegments(points);
    }
    
    // private class LineSegmentNode {
    //     LineSegment lineSeg;
    //     LineSegmentNode next;
    // }
    
    
    private void buildSegments(Point[] points) {
        int n = points.length;
        QuickUnionUF pointsUnion = new QuickUnionUF(n);
        // LineSegmentNode firstSegment = new LineSegmentNode();
        // LineSegmentNode currentNode = firstSegment;
        LineSegment[] theSegs = null;
        LineSegment ls = null;
        int segCount = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                ls = null;
                if (pointsUnion.connected(i, j)) {
                    continue;
                }
                for (int k = j + 1; k < n; k++) {
                    for (int m = k + 1; m < n; m++) {
                        if (isCollinear4Points(points[i], points[j], 
                                               points[k], points[m])) {
                            if (!pointsUnion.connected(i, j)) {
                                pointsUnion.union(i, j);
                            }
                            pointsUnion.union(i, k);
                            pointsUnion.union(i, m);
                            ls = new LineSegment(points[i], points[m]);
                        }
                    }
                }
                if (ls != null) {
                    
                    if (theSegs == null) {
                        theSegs = new LineSegment[] { ls };
                    } else {
                        LineSegment[] tempSegs = new LineSegment[theSegs.length +1];
                        for (int p = 0; p < theSegs.length; p++) {
                            tempSegs[p] = theSegs[p];
                        }
                        tempSegs[theSegs.length] = ls;
                        theSegs = tempSegs;
                    }
                    segCount++;
                }
            }
        }
        lineSegments = theSegs;
        segmentsCount = segCount;
    }
    
    /*
    private void buildSegments(Point[] points) {
        if (isCollinear3Points(points[0], points[1], points[2])) {
            if (isCollinear3Points(points[0], points[1], points[3])) {
                segmentsCount = 1;
                lineSegments = new LineSegment[] {
                    new LineSegment(points[0], points[3])
                };
            } else {
                segmentsCount = 4;
                lineSegments = new LineSegment[] {
                    new LineSegment(points[0], points[2]),
                    new LineSegment(points[0], points[3]),
                    new LineSegment(points[1], points[3]),
                    new LineSegment(points[2], points[3])
                };
            }
        } else if (isCollinear3Points(points[0], points[1], points[3])) {
            segmentsCount = 4;
            lineSegments = new LineSegment[] {
                new LineSegment(points[0], points[3]),
                new LineSegment(points[0], points[2]),
                new LineSegment(points[1], points[2]),
                new LineSegment(points[2], points[3])
            };
        } else if (isCollinear3Points(points[0], points[2], points[3])) {
            segmentsCount = 4;
            lineSegments = new LineSegment[] {
                new LineSegment(points[0], points[3]),
                new LineSegment(points[0], points[1]),
                new LineSegment(points[1], points[2]),
                new LineSegment(points[1], points[3])
            };
        } else if (isCollinear3Points(points[1], points[2], points[3])) {
            segmentsCount = 4;
            lineSegments = new LineSegment[] {
                new LineSegment(points[1], points[3]),
                new LineSegment(points[0], points[1]),
                new LineSegment(points[0], points[2]),
                new LineSegment(points[0], points[3])
            };
        } else {
            segmentsCount = 6;
            lineSegments = new LineSegment[] {
                new LineSegment(points[0], points[1]),
                new LineSegment(points[0], points[2]),
                new LineSegment(points[0], points[3]),
                new LineSegment(points[1], points[2]),
                new LineSegment(points[1], points[3]),
                new LineSegment(points[2], points[3])
            };
        }
    }
    */
    
    private boolean isCollinear4Points(Point a, Point b, Point c, Point d) {
        Comparator<Point> aCmp = a.slopeOrder();
        if (aCmp.compare(b, c) == 0 && aCmp.compare(b, d) == 0) {
            return true;
        }
        
        return false;
    }
    /*
    private boolean isCollinear3Points(Point a, Point b, Point c) {
        Comparator<Point> aCmp = a.slopeOrder();
        if (aCmp.compare(b, c) == 0) {
            return true;
        }
        
        return false;
    }
    */
    public int numberOfSegments() {
        return segmentsCount;
    }
    
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
        // n = 4;
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
        // for (Point p : points) {
        //      p.draw();
        //  }
        StdDraw.show();
        
        // print and draw the line segments
        // for (int i = 0; i < n /4; i++) {
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        // }
        StdDraw.show();
    }
    
}