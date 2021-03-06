/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (that == null) {
            throw new NullPointerException("Null point");
        }
        boolean equalX = ((this.x - that.x) == 0);
        boolean equalY = ((this.y - that.y) == 0);

        if (equalX && equalY) return Double.NEGATIVE_INFINITY;
        else if (equalX) return Double.POSITIVE_INFINITY;
        else if (equalY) return +0.0;
        else return (double) (this.y - that.y) / (this.x - that.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        if (that == null) {
            throw new NullPointerException("Null point");
        }
        if (this.y == that.y) {
            if ((this.x - that.x) > 0) return 1;
            else if ((this.x - that.x) < 0) return -1;
            else return 0;
        } else {
            if ((this.y - that.y) > 0) return 1;
            else if ((this.y - that.y) < 0) return -1;
            else return 0;
        }
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SortBySlope();
    }

    private class SortBySlope implements Comparator<Point> {

        public int compare(Point a, Point b) {
            if ((a == null) || (b == null)) {
                throw new NullPointerException("Null point");
            }

            double slopeA = slopeTo(a);
            double slopeB = slopeTo(b);
            if ((slopeA - slopeB) > 0) return 1;
            else if ((slopeA - slopeB) < 0) return -1;
            else return 0;
        }
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
//        assert a.slopeTo(b) == +0.0;
//        ArrayList<Point> ar = new ArrayList<Point>();
//        ar.add(new Point(-1, -1));
//        ar.add(new Point(-1, 1));
//        ar.add(new Point(-5, 1));
//        ar.add(new Point(1, 2));
//        ar.add(new Point(1, 1));
//        ar.add(new Point(1, -1));
//        ar.add(new Point(-2, -1));
//        ar.add(new Point(-1, 3));
//
//        System.out.println("Unsorted");
//        for (int i = 0; i < ar.size(); i++) {
//            System.out.println(ar.get(i));
//        }
//
//        Collections.sort(ar, new sortByXy());
//
//        System.out.println("Sorted");
//        for (int i = 0; i < ar.size(); i++) {
//            System.out.println(ar.get(i));
//        }
//
//        Collections.shuffle(ar);
//        System.out.println("Check Slop to:");
//        for (int i = 0; i < ar.size() - 1; i++) {
//            System.out.println(ar.get(i).toString() + ar.get(i + 1));
//            System.out.println(ar.get(i).slopeTo(ar.get(i + 1)));
//        }
//        System.out.println(ar.get(1).toString() + ar.get(1));
//        System.out.println(ar.get(1).slopeTo(ar.get(1)));
//
//        Point a = new Point(0, 40);
//        Point b = new Point(0, 60);
//        Point c = new Point(20, 0);
//        System.out.println("check compare to");
//        System.out.println(a.compareTo(b));
//        System.out.println(b.compareTo(c));

        int[][] myArray = new int[3][4];
        System.out.println(myArray.length);

        for (int i = 0; i < 5; i++) {
            System.out.println(StdRandom.uniform(0, 3));
        }

    }
}
