import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;


public class KdTree {

    private class Node {
        private final Point2D pt;
        private final boolean splitByX;  // vertical split, use x
        private Node left, right;

        Node(Point2D p, boolean v) {
            pt = new Point2D(p.x(), p.y());
            splitByX = v;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;


    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;

    }

    private void put(Point2D point) {
        root = put(root, point, true);
    }

    // this put can be further optimized with LLRBT
    private Node put(Node node, Point2D point, boolean v) {
        if (node == null) {
            size++;
            return new Node(point, v); // BUG here: need init splitByX here
        }
        int cmp;
        if (node.splitByX)
            cmp = Double.compare(point.x(), node.pt.x());
        else
            cmp = Double.compare(point.y(), node.pt.y());

        if (cmp < 0)
            node.left = put(node.left, point, !node.splitByX);
        else if (cmp > 0)
            node.right = put(node.right, point, !node.splitByX);
        else {
            if (node.splitByX) {
                int cmpY = Double.compare(point.y(), node.pt.y());
                if (cmpY < 0)
                    node.left = put(node.left, point, !node.splitByX);
                else if (cmpY > 0)
                    node.right = put(node.right, point, !node.splitByX);
                else
                    return node;
            } else {
                int cmpX = Double.compare(point.x(), node.pt.x());
                if (cmpX < 0)
                    node.left = put(node.left, point, !node.splitByX);
                else if (cmpX > 0)
                    node.right = put(node.right, point, !node.splitByX);
                else
                    return node;
            }
        }
        return node;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;

    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        put(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {

        Node node = root;
        while (node != null) {
            if (p.compareTo(node.pt) == 0) {
                return true;
            }
            int cmp;
            if (node.splitByX)
                cmp = Double.compare(p.x(), node.pt.x());
            else
                cmp = Double.compare(p.y(), node.pt.y());

            if (cmp < 0)
                node = node.left;
            else if (cmp > 0)
                node = node.right;
            else {
                if (node.splitByX) {
                    int cmpY = Double.compare(p.y(), node.pt.y());
                    if (cmpY < 0)
                        node = node.left;
                    else if (cmpY > 0)
                        node = node.right;
                    else
                        return true;
                } else {
                    int cmpX = Double.compare(p.x(), node.pt.x());
                    if (cmpX < 0)
                        node = node.left;
                    else if (cmpX > 0)
                        node = node.right;
                    else
                        return true;
                }
            }
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        drawTree(root, new RectHV(0, 0, 1, 1));
    }

    // needs to be recursive draw and generate rectBound during iteration
    private void drawTree(Node node, RectHV nodeRect) {
        if (node == null) return;

        StdDraw.setPenColor(StdDraw.BLACK);
//        StdDraw.setPenRadius(0.008);
        node.pt.draw();

        // draw current node
        RectHV leftNodeRect;
        RectHV rightNodeRect;
        if (node.splitByX) {
            Point2D start = new Point2D(node.pt.x(), nodeRect.ymax());
            Point2D end = new Point2D(node.pt.x(), nodeRect.ymin());
//            StdDraw.setPenRadius(0.003);
            StdDraw.setPenColor(StdDraw.RED);
            start.drawTo(end);

            leftNodeRect = new RectHV(nodeRect.xmin(), nodeRect.ymin(), node.pt.x(), nodeRect.ymax());
            rightNodeRect = new RectHV(node.pt.x(), nodeRect.ymin(), nodeRect.xmax(), nodeRect.ymax());
        } else {
            Point2D start = new Point2D(nodeRect.xmax(), node.pt.y());
            Point2D end = new Point2D(nodeRect.xmin(), node.pt.y());
//            StdDraw.setPenRadius(0.003);
            StdDraw.setPenColor(StdDraw.BLUE);
            start.drawTo(end);

            leftNodeRect = new RectHV(nodeRect.xmin(), nodeRect.ymin(), nodeRect.xmax(), node.pt.y());
            rightNodeRect = new RectHV(nodeRect.xmin(), node.pt.y(), nodeRect.xmax(), nodeRect.ymax());
        }

        // draw child node
        drawTree(node.left, leftNodeRect);
        drawTree(node.right, rightNodeRect);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> retList = new ArrayList<>();

        rangeSearch(root, new RectHV(0, 0, 1, 1), rect, retList);
        return retList;
    }

    private void rangeSearch(Node node, RectHV nodeRect, RectHV rectRange, List<Point2D> ptList) {
        if (node == null) return;

        // draw current node
        RectHV leftNodeRect;
        RectHV rightNodeRect;

        if (rectRange.contains(node.pt))
            ptList.add(node.pt);

        if (node.splitByX) {

            leftNodeRect = new RectHV(nodeRect.xmin(), nodeRect.ymin(), node.pt.x(), nodeRect.ymax());
            rightNodeRect = new RectHV(node.pt.x(), nodeRect.ymin(), nodeRect.xmax(), nodeRect.ymax());
        } else {


            leftNodeRect = new RectHV(nodeRect.xmin(), nodeRect.ymin(), nodeRect.xmax(), node.pt.y());
            rightNodeRect = new RectHV(nodeRect.xmin(), node.pt.y(), nodeRect.xmax(), nodeRect.ymax());
        }

        if (rectRange.intersects(leftNodeRect)) {
            rangeSearch(node.left, leftNodeRect, rectRange, ptList);
        }
        if (rectRange.intersects(rightNodeRect)) {
            rangeSearch(node.right, rightNodeRect, rectRange, ptList);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        RectHV initRect = new RectHV(0, 0, 1, 1);
//        System.out.println("-----initP:" + p.toString());
        Point2D nearestPt = root.pt;
        nearestPt = nearestSearch(root, initRect, p, nearestPt);
//        System.out.println("-----nearestPt:" + nearestPt.toString());
        return nearestPt;
    }

    private Point2D nearestSearch(Node node, RectHV nodeRect, Point2D p, Point2D nearestPt) {

        if (node == null) {
//            System.out.println("    Find Null return.");
            return null;
        }

//        System.out.println("-----Current node:" + node.pt.toString());
//        System.out.println("DistanceCheck:" + Double.toString(p.distanceSquaredTo(nearestPt)) +
//                " " + Double.toString(p.distanceSquaredTo(node.pt)));
        if (p.distanceSquaredTo(nearestPt) > p.distanceSquaredTo(node.pt)) {
//
            nearestPt = new Point2D(node.pt.x(), node.pt.y());
        }

//        System.out.println("nearestPt:" + nearestPt.toString());


        // draw current node
        RectHV leftNodeRect;
        RectHV rightNodeRect;

//        StdDraw.setPenColor(StdDraw.BLACK);
//        StdDraw.setPenRadius(0.008);
//        node.pt.draw();

        if (node.splitByX) {

//            Point2D start = new Point2D(node.pt.x(), nodeRect.ymax());
//            Point2D end = new Point2D(node.pt.x(), nodeRect.ymin());
//            StdDraw.setPenRadius(0.003);
//            StdDraw.setPenColor(StdDraw.RED);
//            start.drawTo(end);

            leftNodeRect = new RectHV(nodeRect.xmin(), nodeRect.ymin(), node.pt.x(), nodeRect.ymax());
            rightNodeRect = new RectHV(node.pt.x(), nodeRect.ymin(), nodeRect.xmax(), nodeRect.ymax());

        } else {

//            Point2D start = new Point2D(nodeRect.xmax(), node.pt.y());
//            Point2D end = new Point2D(nodeRect.xmin(), node.pt.y());
//            StdDraw.setPenRadius(0.003);
//            StdDraw.setPenColor(StdDraw.BLUE);
//            start.drawTo(end);

            leftNodeRect = new RectHV(nodeRect.xmin(), nodeRect.ymin(), nodeRect.xmax(), node.pt.y());
            rightNodeRect = new RectHV(nodeRect.xmin(), node.pt.y(), nodeRect.xmax(), nodeRect.ymax());
        }

//        System.out.println("leftRect:" + leftNodeRect.toString());
//        System.out.println("rightRect:" + rightNodeRect.toString());

//        StdDraw.show();
//        StdDraw.pause(50);

        if (p.distanceSquaredTo(nearestPt) > leftNodeRect.distanceSquaredTo(p)) {
//            System.out.println("go Left:" + Double.toString(p.distanceSquaredTo(nearestPt)) +
//                    " " + Double.toString(leftNodeRect.distanceSquaredTo(p)));
//            System.out.println(leftNodeRect.toString());
//            System.out.println("nearestPt:" + nearestPt.toString());
            if (node.left != null) {
                nearestPt = nearestSearch(node.left, leftNodeRect, p, nearestPt);
            }

        }

        if (p.distanceSquaredTo(nearestPt) > rightNodeRect.distanceSquaredTo(p)) {
//            System.out.println("go right:" + Double.toString(p.distanceSquaredTo(nearestPt)) +
//                    " " + Double.toString(rightNodeRect.distanceSquaredTo(p)));
//            System.out.println(rightNodeRect.toString());
//            System.out.println("nearestPt:" + nearestPt.toString());

            if (node.right != null) {
                nearestPt = nearestSearch(node.right, rightNodeRect, p, nearestPt);
            }

        }

        return nearestPt;
    }


    // unit testing of the methods (optional)
    public static void main(String[] args) {
        // initialize the data structures from file
        String filename = args[0];
        In in = new In(filename);
        KdTree myTree = new KdTree();

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);

            myTree.insert(p);
        }

        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        myTree.draw();
        StdDraw.show();

        // process range search queries
        StdDraw.enableDoubleBuffering();

        Point2D queryPt = new Point2D(0, 0);
        Point2D near = myTree.nearest(queryPt);
        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.setPenRadius(0.03);
        near.draw();
        StdDraw.show();
    }
}
