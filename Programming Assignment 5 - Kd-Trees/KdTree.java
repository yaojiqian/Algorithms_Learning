/******************************************************************************
 *  Name:    Yao Jiqian
 *  NetID:   JQ
 *  Precept: P05
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description: A 2d-tree is a generalization of a BST to two-dimensional keys. 
 *               The idea is to build a BST with points in the nodes, using the 
 *               x- and y-coordinates of the points as keys in strictly 
 *               alternating sequence. 
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
// import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    
    private final Node root;
    private int sz;
    
    private class Node {
        Point2D point;
        Node left;
        Node right;
        
        public Node(Point2D p) {
            point = p;
            left = null;
            right = null;
        }
    }
    
    // private enum SearchDirection { X_Orientation, Y_Orientation }
    
    // construct an empty set of points 
    public KdTree() {
        root = new Node(null);
        sz = 0;
    }
    
    // is the set empty?
    public boolean isEmpty() {
        return root.point == null;
        // if (root.point == null) {
        //    return true;
        // } else {
        //    return false;
        // }
    }
    
    // number of points in the set 
    public int size() {
        return sz;
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        
        // if (contains(p)) return;
        
        if (root.point == null) {
            root.point = p;
            sz++;
        } else {
            boolean isVertical = true;
            Node current = root;
            // Node pre = root;
            // while (current != null) {
            do {
                double newValue, curValue;
                if (isVertical) {
                    newValue = p.x();
                    curValue = current.point.x();
                    if (Double.compare(newValue, curValue) == 0 && 
                            Double.compare(p.y(), current.point.y()) == 0)
                    {
                        break;
                    }
                    isVertical = false;
                } else {
                    newValue = p.y();
                    curValue = current.point.y();
                    if (Double.compare(newValue, curValue) == 0 && 
                            Double.compare(p.x(), current.point.x()) == 0)
                    {
                        break;
                    }
                    isVertical = true;
                }
                // pre = current;
                if (newValue < curValue) {
                    if (current.left != null) {
                        current = current.left;
                    }
                    else {
                        Node newNode = new Node(p);
                        current.left = newNode;
                        sz++;
                        break;
                    }
                } else {
                    if (current.right != null) {
                        current = current.right;
                    } else {
                        Node newNode = new Node(p);
                        current.right = newNode;
                        sz++;
                        break;
                    }
                }
            } while (current != null);
        }
    }
    
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        
        boolean isVertical = true;
        Node current = root;
        while (current != null) {
            if (current.point == null) {
                current = null;
                break;
            }
            if (p.compareTo(current.point) == 0)
                break;
            double newValue, curValue;
            if (isVertical) {
                newValue = p.x();
                curValue = current.point.x();
                isVertical = false;
            } else {
                newValue = p.y();
                curValue = current.point.y();
                isVertical = true;
            }
            if (newValue < curValue) {
                // if (current.left != null) {
                    current = current.left;
                // }
            } else {
                // if (current.right != null) {
                    current = current.right;
                // }
            }
        }
        return current != null;
        //    return false;
        // else
        //    return true;
    }
    
    // draw all points to standard draw
    public void draw() {
        StdDraw.setScale(0.0, 1.0);
        innerDraw(root, true);
    }
    
    // private void innerDraw(Node tree, Point2D parent, boolean isVertical) {
    private void innerDraw(Node tree, boolean isVertical) {
        
        if (tree == null) return;
        
        Point2D p = tree.point;
        
        if (p == null) return;
                
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(p.x(), p.y());
        /*
        StdDraw.setPenRadius(0.002);
        if (isVertical) {
            StdDraw.setPenColor(StdDraw.BLUE);
            if (parent == null) {
                StdDraw.line(p.x(), 0.0, p.x(), 1.0);
            } else if (p.y() <= parent.y()) {
                StdDraw.line(p.x(), 0.0, p.x(), parent.y());
            } else {
                StdDraw.line(p.x(), parent.y(), p.x(), 1.0);
            }
        } else {
            StdDraw.setPenColor(StdDraw.RED);
            if (parent == null) {
                StdDraw.line(0.0, p.y(), 1.0, p.y());
            } else if (p.x() <= parent.x()) {
                StdDraw.line(0.0, p.y(), parent.x(), p.y());
            } else {
                StdDraw.line(parent.x(), p.y(), 1.0, p.y());
            }
        }*/
        innerDraw(tree.right, !isVertical);
        innerDraw(tree.left, !isVertical);        
    }
    
    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.IllegalArgumentException();
        return innerPoints(rect, root, true);
    }
    
    private Stack<Point2D> innerPoints(RectHV rect, Node tree, boolean isVertical) {
        Stack<Point2D> pSet = new Stack<Point2D>();
        // Stack<Point2D> rSet = null;
        Point2D p = tree.point;
        
        if (p == null) return pSet;
        
        if (isVertical) {
            if (rect.xmin() <= p.x() && tree.left != null) {
                Stack<Point2D> leftSet = innerPoints(rect, tree.left, false);
                // rSet = pSet.union(leftSet);
                for (Point2D pp : leftSet)
                    pSet.push(pp);
            }
            if (rect.xmax() >= p.x() && tree.right != null) {
                Stack<Point2D> rightSet = innerPoints(rect, tree.right, false);
                // rSet = pSet.union(rightSet);
                for (Point2D pp : rightSet)
                    pSet.push(pp);
            }
        } else {
            if (rect.ymin() <= p.y() && tree.left != null) {
                Stack<Point2D> bottomSet = innerPoints(rect, tree.left, true);
                // rSet = pSet.union(bottomSet);
                for (Point2D pp : bottomSet)
                    pSet.push(pp);
            }
            if (rect.ymax() >= p.y() && tree.right != null) {
                Stack<Point2D> upSet = innerPoints(rect, tree.right, true);
                // rSet = pSet.union(upSet);
                for (Point2D pp : upSet)
                    pSet.push(pp);
            }           
        }
        
        if (rect.contains(p)) {
            pSet.push(p);
        }
        
        return pSet;
        
        // if (rSet != null && !rSet.isEmpty()) {
        //    return pSet.union(rSet);
        // } else {
        //     return pSet;
        // }
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        
        return innerNearest(p, root, true);
    }
    
    private Point2D innerNearest(Point2D searchPoint, Node tree, boolean isVertical) {
        
        if (tree == null || tree.point == null) return null;
        
        Point2D curPoint = tree.point;
        
        double curDist = searchPoint.distanceSquaredTo(curPoint);
        
        Node firstNode = null;
        Node nextNode = null;
        Point2D firstPoint;
        Point2D nextPoint = null;
        Point2D linePoint;
        double firstDist;
        double nextDist;
        double distToLine;
        if (isVertical) {
            if (searchPoint.x() <= curPoint.x()) {
                firstNode = tree.left;
                nextNode = tree.right;
            } else {
                firstNode = tree.right;
                nextNode = tree.left;
            }
            linePoint = new Point2D(curPoint.x(), searchPoint.y());
        } else {
            if (searchPoint.y() <= curPoint.y()) {
                firstNode = tree.left;
                nextNode = tree.right;
            } else {
                firstNode = tree.right;
                nextNode = tree.left;
            }
            linePoint = new Point2D(searchPoint.x(), curPoint.y());
        }
        
        firstPoint = innerNearest(searchPoint, firstNode, !isVertical);
        firstDist = Double.POSITIVE_INFINITY;
        nextDist = Double.POSITIVE_INFINITY;
        if (firstPoint != null)
            firstDist = searchPoint.distanceSquaredTo(firstPoint);
            
        distToLine = searchPoint.distanceSquaredTo(linePoint);
        if (distToLine < firstDist)
            nextPoint = innerNearest(searchPoint, nextNode, !isVertical);
        if (nextPoint != null)
            nextDist = searchPoint.distanceSquaredTo(nextPoint);
        
        if (curDist <= firstDist && curDist <= nextDist) {
            return curPoint;
        } else if (firstDist <= nextDist) {
            return firstPoint;
        } else {
            return nextPoint;
        }
    }
    
    // unit testing of the methods (optional) 
    public static void main(String[] args) {
        
        KdTree pSet = new KdTree();
        In in = new In(args[0]);
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            pSet.insert(new Point2D(x, y));
        }
        
        StdOut.println(pSet.size());
        
        pSet.draw();
        
        Point2D searchPoint = new Point2D(0.323513, 0.448795);
        StdOut.printf("pSet contains %s, %b\n", searchPoint, 
                      pSet.contains(searchPoint));
        
        RectHV range = new RectHV(0.375, 0.75, 0.625, 0.875);
        StdDraw.setPenRadius(0.001);
        StdDraw.setPenColor(StdDraw.RED);
        range.draw();
        StdOut.println("=========================================");
        for (Point2D p :pSet.range(range)) {
            StdOut.println(p.toString());
        }
        
    }
}