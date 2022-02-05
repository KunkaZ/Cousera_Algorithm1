import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class FastCollinearPoints {

    private int numOfSeg;
    private final Queue<LineSegment> lineSegs;
    private final Point[] points;

    private static class PointPair {
        public final Point start;   // one endpoint of this line segment
        public final Point end;   // the other endpoint of this line segment

        PointPair(Point s, Point e) {
            start = s;
            end = e;
        }
    }

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        if (points == null) {
            throw new IllegalArgumentException("Null argument");
        }

        for (Point p : points) {
            if (p == null)
                throw new IllegalArgumentException("Found null entries");
        }

        Arrays.sort(points);
        for (int p = 0; p < points.length - 1; p++) {

            // duplicate points check
            if (points[p].compareTo(points[p + 1]) == 0)
                throw new IllegalArgumentException("No duplicate points are allowed");
        }

        numOfSeg = 0;
        lineSegs = new LinkedList<>();
        this.points = Arrays.copyOf(points, points.length);
        if (points.length < 4) {
            return;
        }
        numOfSeg = getCollinearPoints();
    }

    private int getCollinearPoints() {

//        PointPair[] savedSegPts = new PointPair[points.length];
        Queue<PointPair> savedSegPts = new LinkedList<>();
        for (int p = 0; p < points.length - 3; p++) {
            // only sort a subset of the points that was not checked
            Arrays.sort(points, p, points.length);
//            System.out.println(Arrays.toString(points));
            Arrays.sort(points, p + 1, points.length, points[p].slopeOrder());

//            System.out.println(Arrays.toString(points));

            int collinearPtsCount = 2; // one line segment has two points
            double tempSlope = points[p].slopeTo(points[p + 1]);

//            System.out.println("-----Outer Loop Start Point:" + points[p].toString() + points[p + 1].toString());

            Point startPt, endPt;
            if (points[p].compareTo(points[p + 1]) > 0) {
                startPt = points[p + 1];
                endPt = points[p];
            } else {
                startPt = points[p];
                endPt = points[p + 1];
            }
            for (int i = p + 2; i < points.length; i++) {

//                System.out.println("--" + Integer.toString(i) + " Inner Loop Points:" + points[i].toString());
//                System.out.println("Slope:" + Double.toString(tempSlope) + ", " + Double.toString(points[p].slopeTo(points[i])));

                if (tempSlope == points[p].slopeTo(points[i])) {

                    collinearPtsCount++;
//                    System.out.println("startPt: " + startPt.toString() + " endPt:" + endPt.toString());
                    // find new start and end
                    if (startPt.compareTo(points[i]) > 0) startPt = points[i];
                    if (endPt.compareTo(points[i]) < 0) endPt = points[i];

//                    System.out.println("startPt: " + startPt.toString() + " endPt: " + endPt.toString());

                    // take care case: the N>=4 points are collinear
                    if ((collinearPtsCount >= 4) && (i == points.length - 1)) {
                        if (!checkDupSeg(startPt, endPt, savedSegPts)) {
//                            System.out.println("Last Seg:" + Integer.toString(collinearPtsCount));
                            // generate Segment
                            savedSegPts.add(new PointPair(startPt, endPt));
                            lineSegs.add(new LineSegment(startPt, endPt));
//                            System.out.println("lineSegs:" + lineSegs[numOfSeg].toString());
                        }
                    }
                } else {
//                    System.out.println("collinearPtsCount:" + Integer.toString(collinearPtsCount));
                    if (collinearPtsCount >= 4) {
                        // Problem: line seg a->b->c->d->e, since sorted by xy (a<b<c<d<e), the line seg will grow sequencially from
                        // a to e, so the end point will always be the max point e, even with subarray sort
                        // so we only need to compare end point to eliminate duplicate segments
                        // we always find the max segment first

                        if (!checkDupSeg(startPt, endPt, savedSegPts)) {
                            // generate Segment
                            savedSegPts.add(new PointPair(startPt, endPt));
                            lineSegs.add(new LineSegment(startPt, endPt));
//                            System.out.println("lineSegs:" + lineSegs[numOfSeg].toString());
//                            System.out.println(Arrays.toString(points));
                        }
                    }

                    // reset when find non-collinear points
                    collinearPtsCount = 2;
                    tempSlope = points[p].slopeTo(points[i]);

                    if (points[p].compareTo(points[i]) > 0) {
                        startPt = points[i];
                        endPt = points[p];
                    } else {
                        startPt = points[p];
                        endPt = points[i];
                    }
                }
//                System.out.println("End Out loop NumOfSeg:" + Integer.toString(numOfSeg));
            }

        }
        numOfSeg = lineSegs.size();
        return numOfSeg;
    }

    private boolean checkDupSeg(Point startPt, Point endPt, Queue<PointPair> savedSegPts) {
        boolean dupSeg = false;
        if (savedSegPts.size() != 0) {
            for (PointPair temp : savedSegPts) {
                if ((temp.end == endPt)
                        && (startPt.slopeTo(endPt) == temp.end.slopeTo(temp.start))) {
                    dupSeg = true;
                    break;
                }
            }
        }
        return dupSeg;
    }

    // the number of line segments
    public int numberOfSegments() {
        return numOfSeg;

    }

    // the line segments
    public LineSegment[] segments() {
        if (lineSegs.size() != 0) {
            LineSegment[] retSegs = new LineSegment[numOfSeg];
            int i = 0;
            for (LineSegment lineSeg : lineSegs) {
                retSegs[i++] = lineSeg;
            }

            return retSegs;
        } else {
            return new LineSegment[0];
        }
    }

    public static void main(String[] args) {

//        int numOfPts = StdIn.readInt();
//        Point[] points = new Point[numOfPts];
//        while (!StdIn.isEmpty() && (numOfPts > 0)) {
//            int x = StdIn.readInt();
//            int y = StdIn.readInt();
//            points[numOfPts - 1] = new Point(x, y);
////            System.out.println(points[numOfPts - 1].toString());
//            numOfPts--;
//        }

        int m = 4;
        int n = 1;
        Point[] points = new Point[m * n];

        int k = 0;
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {

                points[k++] = new Point(x, y);

            }
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(1024, 1024);
        StdDraw.setScale(0, 30000d);
        StdDraw.setPenRadius(0.03);
        StdDraw.setXscale(-10, 20);
        StdDraw.setYscale(-10, 20);
        StdDraw.setPenColor(StdDraw.BLUE);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.RED);

        FastCollinearPoints colCheck = new FastCollinearPoints(points);

        LineSegment[] lineSegs = colCheck.segments();
        for (int i = 0; i < lineSegs.length; i++) {
            lineSegs[i].draw();
            System.out.println(lineSegs[i].toString());
        }
        System.out.println(lineSegs.length);
        StdDraw.show();


        points[3] = new Point(10, 0);

        System.out.println(Arrays.toString(lineSegs));
        lineSegs = colCheck.segments();
        System.out.println(Arrays.toString(lineSegs));

//        Point[] pts1 = new Point[2];
//        Point[] pts2 = new Point[2];
//
//        pts1[0] = new Point(0, 0);
//        pts1[1] = new Point(0, 1);
//        pts2[0] = new Point(2, 0);
//        pts2[1] = new Point(2, 0);
//        System.out.println(pts1);
//        System.out.println(pts2);
//        System.out.println(Arrays.toString(pts1));
//        System.out.println(Arrays.toString(pts2));
//
//        pts2[0] = pts1[0];
//        pts2[1] = pts1[1];
//
//        System.out.println(pts1);
//        System.out.println(pts2);
//        System.out.println(Arrays.toString(pts1));
//        System.out.println(Arrays.toString(pts2));

    }
}
