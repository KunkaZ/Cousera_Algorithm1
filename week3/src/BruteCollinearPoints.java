import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BruteCollinearPoints {

    private final int numOfSeg;
    private final Queue<LineSegment> lineSegs;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {


        if (points == null) {
            throw new IllegalArgumentException("argument to Collinear Points constructor is null");
        }

        for (Point p : points) {
            if (p == null)
                throw new IllegalArgumentException("Found null entries");
        }

        Point[] pts = Arrays.copyOf(points, points.length);
        Arrays.sort(pts);

        for (int i = 0; i < pts.length - 1; i++) {

            if (pts[i].compareTo(pts[i + 1]) == 0)
                throw new IllegalArgumentException("No duplicate points are allowed");
        }

//
        lineSegs = new LinkedList<>();
        Arrays.sort(pts);
//        System.out.println("length:" + points.length);
        for (int i = 0; i < pts.length - 3; i++) {
//            System.out.println("------Loop:" + i);
            for (int j = i + 1; j < pts.length; j++) {
                for (int k = j + 1; k < pts.length; k++) {
                    for (int m = k + 1; m < pts.length; m++) {
                        double s1, s2, s3;
                        s1 = pts[i].slopeTo(pts[j]);
                        s2 = pts[j].slopeTo(pts[k]);
                        s3 = pts[k].slopeTo(pts[m]);

                        if (((s1 == Double.POSITIVE_INFINITY) && (s2 == Double.POSITIVE_INFINITY) && (s3 == Double.POSITIVE_INFINITY))
                                || ((s1 == s2) && (s2 == s3))) {

//                            System.out.println("i:" + points[i].toString() + " m:" + points[m].toString());
//                            System.out.println("Slope:" + "s1:" + s1 + " s2:" + s2 + " s3:" + s3);
//
//                            System.out.println("--found:");
                            lineSegs.add(new LineSegment(pts[i], pts[m]));
                        }
                    }
                }
            }
        }
        numOfSeg = lineSegs.size();
    }

    // the number of line segments
    public int numberOfSegments() {
        return numOfSeg;
    }

    // the line segments
    public LineSegment[] segments() {

        if (numOfSeg != 0) {
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
/*
        Point[] points = new Point[8];

        System.out.println("Colline horizontal:");
        for (int i = 0; i < 4; i++) {
            points[i] = new Point(0, i);
        }
        for (int i = 0; i < 4; i++) {
            points[4 + i] = new Point(1, i);
        }
        System.out.println(Arrays.toString(points));
        BruteCollinearPoints colPts = new BruteCollinearPoints(points);
        LineSegment[] temp = colPts.segments();
        System.out.println(Arrays.toString(temp));

        System.out.println("Colline vertical:");
        for (int i = 0; i < 4; i++) {
            points[i] = new Point(i, 1);
            System.out.println(points[i].toString());
        }
        colPts = new BruteCollinearPoints(points);
        temp = colPts.segments();
        System.out.println(temp[0]);

        System.out.println("Colline 45 deg:");
        for (int i = 0; i < 4; i++) {
            int x = StdRandom.uniform(-5, 10);
            points[i] = new Point(x, x);
            System.out.println(points[i].toString());
        }
        colPts = new BruteCollinearPoints(points);
        temp = colPts.segments();
        System.out.println(temp[0]);

        System.out.println("Colline 135 deg:");
        for (int i = 0; i < 4; i++) {
            int x = StdRandom.uniform(-5, 10);
            points[i] = new Point(x, -x);
            System.out.println(points[i].toString());
        }

        colPts = new BruteCollinearPoints(points);
        temp = colPts.segments();
        System.out.println(temp[0]);
*/
    }
}
