/******************************************************************************
 *  Name:    Yao Jiqian
 *  NetID:   JQ
 *  Precept: P05
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description: represents a set of points in the unit square. 
 *               Implement the following API by using a red-black BST
 ******************************************************************************/

import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
// import java.util.Iterator;
// import edu.princeton.cs.algs4.Stack;

public class PointSET {
    
    private final SET<Point2D> pointSet;
    
    // construct an empty set of points
    public PointSET() {
        
        pointSet = new SET<Point2D>();
    }
    
    // is the set empty?
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }
    
    // number of points in the set
    public int size() {
        return pointSet.size();
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        pointSet.add(p);
    }
    
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        return pointSet.contains(p);
    }
    
    // draw all points to standard draw
    public void draw() {
        StdDraw.setScale(0.0, 1.0);
        StdDraw.setPenRadius(0.01d);
        for (Point2D p : pointSet) {
            StdDraw.point(p.x(), p.y());
        }
        /*
        StdOut.printf("Points Count: %s \n", this.size());
        for (Point2D p : pointSet) {
            StdOut.println(p.toString());
            StdDraw.text(p.x(), p.y(), p.toString());
        }
        */
    }
    
    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.IllegalArgumentException();
        
        // new a Stack to return the points.
        SET<Point2D> points = new SET<Point2D>();
        
        // Point2D rectMinPoint = new Point2D(rect.xmin(), rect.ymin());
        // Point2D rectMaxPoint = new Point2D(rect.xmax(), rect.ymax());
        // if (pointSet.max().compareTo(rectMinPoint) < 0 || 
        //         rectMaxPoint.compareTo(pointSet.min()) < 0) {
        //     return points;
        // }
        
        // Iterator<Point2D> it = pointSet.iterator();
        
        for (Point2D p : pointSet) {
            if (rect.contains(p)) {
                points.add(p);
            }
        }
        
        return points;
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        
        double dist = Double.POSITIVE_INFINITY;
        Point2D rp = null;
        
        for (Point2D pp : pointSet) {
            double td = pp.distanceSquaredTo(p);
            if (td < dist) {
                dist = td;
                rp = pp;
            }
        }

        return rp;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        
        PointSET pSet = new PointSET();
        In in = new In(args[0]);
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            pSet.insert(new Point2D(x, y));
        }
        pSet.draw();
        
        RectHV range = new RectHV(0.25, 0.25, 0.5, 0.5);
        StdDraw.setPenRadius(0.001d);
        StdDraw.setPenColor(StdDraw.RED);
        range.draw();
        StdOut.println("=========================================");
        for (Point2D p :pSet.range(range)) {
            StdOut.println(p.toString());
        }
        
    }
}