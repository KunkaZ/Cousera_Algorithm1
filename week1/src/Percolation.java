import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF siteUnion;
    private final boolean[][] siteStatus;
    private final int dimN;
    private int numOpenSites;
    private final int top;
    private final int bottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Negative Input");
        }

        siteStatus = new boolean[n][n];
        dimN = n;
        top = dimN * dimN;
        bottom = dimN * dimN + 1;
        siteUnion = new WeightedQuickUnionUF(dimN * dimN + 2);
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkBoundary(row, col, true);
        if (!isOpen(row, col)) {

            siteStatus[row - 1][col - 1] = true;
            numOpenSites++;
            int ind = getUnionInd(row, col);

            // if first row or last row, need to union, dimN or dimN+1 sites
            if (row == 1) union(row, col, top);
            if (row == dimN) union(row, col, bottom);

            union(row - 1, col, ind);
            union(row + 1, col, ind);
            union(row, col - 1, ind);
            union(row, col + 1, ind);
        }
    }

    private void union(int row, int col, int ind) {
        if (!checkBoundary(row, col, false) || !isOpen(row, col))
            return;
        siteUnion.union(getUnionInd(row, col), ind);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkBoundary(row, col, true);
        return siteStatus[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkBoundary(row, col, true);

        // check if connected to site dimN, top node
        int ind = getUnionInd(row, col);
        return (siteUnion.find(ind) == siteUnion.find(top)) && (isOpen(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {

        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {

        return siteUnion.find(top) == siteUnion.find(bottom);
    }

    private int getUnionInd(int row, int col) {
        row -= 1;
        col -= 1;
        return row * dimN + col;
    }

    private boolean checkBoundary(int row, int col, boolean throwEx) {

        if ((row < 1) || (row > dimN) || (col < 1) || (col > dimN)) {
            if (throwEx) {
                throw new IllegalArgumentException("row:" + Integer.toString(row) + " col:" + Integer.toString(col) + " N:" + Integer.toString(dimN));
            }
            return false;
        }
        return true;
    }

    // test client (optional)
    public static void main(String[] args) {

//        int n = Integer.parseInt(args[0]);
//
//        Percolation model = new Percolation(n);
//
//        for (int row = 0; row < n; row++) {
//            System.out.println(Arrays.toString(model.siteStatus[row]));
//        }
//        int step = 0;
//        while ((!model.percolates()) && (step < n * n)) {
//
//            int row = StdRandom.uniform(1, n + 1);
//            int col = StdRandom.uniform(1, n + 1);
//            System.out.println("Open:[" + Integer.toString(row) + "," + Integer.toString(col) + "]");
//            model.open(row, col);
//
//            for (int kk = 0; kk < n; kk++) {
//                System.out.println(Arrays.toString(model.siteStatus[kk]));
//            }
//            step++;
//        }
//
//        System.out.println("After Open");
//        for (int row = 0; row < n; row++) {
//            System.out.println(Arrays.toString(model.siteStatus[row]));
//        }
//        System.out.println("Num Of Open Sites:" + model.numberOfOpenSites());
//        System.out.println("Is percolates:" + model.percolates());
    }
}
