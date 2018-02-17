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

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class LineSegment {
    
    private Point p1;
    private Point p2;
    
    public LineSegment(Point p, Point q) {
        p1 = p;
        p2 = q;
    }
    
    public void draw() {
        //StdDraw.setPenRadius(0.002);
        //StdDraw.setPenColor(StdDraw.RED);
        
        //StdDraw.line(p1.x, p1.y, p2.x, p2.y);
        p1.draw();
        p2.draw();
        p1.drawTo(p2);
    }
    
    public String toString() {
        String str = p1.toString() + " - "  + p2.toString();
        
        return str;
    }
    
    public static void main(String[] args) {
        Point p1 = new Point(5, 10);
        Point p2 = new Point(10, 30);
        
        LineSegment ls = new LineSegment(p1, p2);
        StdOut.printf("%s\n", ls.toString());
        StdDraw.setScale(0, 50);
        ls.draw();
    }
}