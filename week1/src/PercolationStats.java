import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final double[] siteOpen;
    private final int numTrials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if ((n <= 0) || (trials <= 0)) {
            throw new IllegalArgumentException("Out of range inputs!");
        }
        siteOpen = new double[trials];
        numTrials = trials;
        double totalSize = n * n;
        for (int trl = 0; trl < trials; trl++) {
            Percolation model = new Percolation(n);
            int row, col;
            // perform trails
            while (!model.percolates()) {

                row = StdRandom.uniform(1, n + 1);
                col = StdRandom.uniform(1, n + 1);
                model.open(row, col);
            }
            siteOpen[trl] = model.numberOfOpenSites() / totalSize;
        }
    }

    // sample mean of percolation threshold
    public double mean() {

        return StdStats.mean(siteOpen);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {

        return StdStats.stddev(siteOpen);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {

        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(numTrials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {

        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(numTrials);
    }

    // test client
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int numOfTrl = Integer.parseInt(args[1]);
        PercolationStats perco = new PercolationStats(n, numOfTrl);
        StdOut.printf("mean                     = %.10f\n", perco.mean());
        StdOut.printf("Stddev                   = %.10f\n", perco.stddev());

        System.out.printf("95%% confidence interval  = [%.10f, %.10f]\n", perco.confidenceLo(), perco.confidenceHi());
    }
}
