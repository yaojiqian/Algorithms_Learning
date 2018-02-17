/******************************************************************************
 *  Name:    Yao Jiqian
 *  NetID:   JQ
 *  Precept: P03
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description: Write a program to recognize line patterns in a given 
 *               set of points.
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    
    private final int x;
    private final int y;
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void draw() {
        // StdDraw.setPenRadius(0.01d);
        StdDraw.setPenColor(StdDraw.BLUE);

        StdDraw.point(x, y);
    }
    
    public void drawTo(Point that) {
        // StdDraw.setPenRadius(0.002d);
        StdDraw.setPenColor(StdDraw.RED);
        
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    public String toString() {
        String str = String.format("(%d, %d)", x, y);
        return str;
    }
    
    @Override
    public int compareTo(Point that) {
        if (this.y < that.y) {
            return -1;
        }
        if (this.y > that.y) {
            return 1;
        }
        if (this.x < that.x) {
            return -1;
        }
        if (this.x > that.x) {
            return 1;
        }
        return 0;
    }
    
    public double slopeTo(Point that) {
        
        double dx = (double) (this.x - that.x);
        double dy = (double) (this.y - that.y);
        if (dx == 0 && dy == 0) {
            return Double.NEGATIVE_INFINITY;
        } else if (dx == 0) {
            return Double.POSITIVE_INFINITY;
        } else if (dy == 0) {
            return 0;
        } else {
            return dy / dx;
        }
    }
    
    public Comparator<Point> slopeOrder() {
        return new PointComparator();
    }
    
    private class PointComparator implements Comparator<Point> {
        
        @Override
        public int compare(Point p1, Point p2) {
            double slope1 = slopeTo(p1);
            double slope2 = slopeTo(p2);
            return Double.compare(slope1, slope2);
            // if (slope1 < slope2) {
            //      return 1;
            //  } else if (slope1 > slope2) {
            //      return -1;
            //  } else {
            //      return 0;
            //  }
        }
    }
    
    public static void main(String[] args) {
        Point a = new Point(10, 20);
        Point b = new Point(20, 30);
        
        StdOut.printf("a = %s, b = %s\n", a.toString(), b.toString());
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setScale(0, 50);
        a.draw();
        a.drawTo(b);
    }
}