import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class Board {

    private final int[][] board;
    private final int n;
    private int hamCnt;
    private int manhCnt;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException("Null arguments");
        }
        if (tiles[0].length != tiles.length) {
            throw new IllegalArgumentException("Needs n-by-n array");
        }

        this.n = tiles[0].length;

        // make a deep copy
        board = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = tiles[i][j];
            }
        }

        manhCnt = 0;
        hamCnt = 0;
//        System.out.println("-----Constructor Calculate Manh----- ");
//        printBoard(board);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0) {
                    int target = (i * n + j + 1) % (n * n);
                    if (target != board[i][j]) {

                        hamCnt++;
                        int vertical = Math.abs(i - (board[i][j] - 1) / n);
                        int horizontal = Math.abs(j - (board[i][j] - 1) % n);
                        manhCnt += vertical + horizontal;

//                        System.out.print("target:");
//                        System.out.print(target);
//                        System.out.print(" boardij:");
//                        System.out.print(board[i][j]);
//                        System.out.print(" manhCnt:");
//                        System.out.print(manhCnt);
//                        System.out.print("  ");
                    }
                }
            }
        }
//        System.out.println("  ");
    }

    // string representation of this board
    public String toString() {
        if (board == null)
            throw new NoSuchElementException("Not initialized yet");
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toString(n) + "\n");
        for (int[] row : board) {
            String rowString = Arrays.toString(row)
                    .replace(",", "")  //remove the commas
                    .replace("[", "")  //remove the left bracket
                    .replace("]", "")  //remove the right bracket
                    .trim();
            sb.append(rowString);
            sb.append("\n");
        }
        return sb.toString();
    }

//    private void printBoard(int[][] a) {
//        for (int i = 0; i < a[0].length; i++) {
//            System.out.println(Arrays.toString(a[i]));
//        }
//
//    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {

        return hamCnt;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {

        return manhCnt;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamCnt == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) {
//            System.out.println("y null");
            return false;
        }
        if (!(y instanceof Board)) {
//            System.out.println("not instance");
            return false;
        }

        Board yCpy = (Board) y;
        if (yCpy.n != n) return false;
//        System.out.println("deep equal");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != yCpy.board[i][j]) {
                    return false;
                }
            }
        }


        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        ArrayList<Board> neighborBd = new ArrayList<>();

        int zeroX = 0, zeroY = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                    break;
                }
            }
        }

        // get neighbors cnt
        int neighborCnt = 2;
        if ((zeroX != 0) && (zeroX != (n - 1))) neighborCnt++;
        if ((zeroY != 0) && (zeroY != (n - 1))) neighborCnt++;

//            System.out.print("Check neighbor Cnt:");
//            System.out.println(neighborCnt);
        // go through all possible neighbor, max 4 neighbors, check boundary to drop corner case
        int[] shift = {-1, 0, 1, 0, -1};
        int nbCnt = 0;
        for (int iNb = 0; iNb < 4; iNb++) {

            int x = zeroX + shift[iNb];
            int y = zeroY + shift[iNb + 1];
            if (!((x < 0) || (y < 0) || (x >= n) || (y >= n))) {
                if (nbCnt <= neighborCnt) {//
                    // swap tiles

                    Board tempBd = new Board(board);
                    tempBd.board[zeroX][zeroY] = tempBd.board[x][y];
                    tempBd.board[x][y] = 0;
                    tempBd = new Board(tempBd.board);
                    neighborBd.add(tempBd);
                    nbCnt++;
                }
            }
        }
        return neighborBd;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        int[][] twinBd = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                twinBd[i][j] = board[i][j];
            }
        }

        int i = 0;
        while (twinBd[0][i++] == 0) {
        }

        int j = 0;
        while (twinBd[1][j++] == 0) {
        }

        int temp = twinBd[0][i];
        twinBd[0][i] = twinBd[1][j];
        twinBd[1][j] = temp;

        Board retBd = new Board(twinBd);
        return retBd;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();

        Board initial = new Board(tiles);
        System.out.println(initial);
//
        System.out.println("Test neighbor:");
        Iterable<Board> checkNeighbor = initial.neighbors();

        System.out.println("Test iterable:");
        for (Board bd : checkNeighbor) {
            System.out.println("---");
            System.out.print("manh:");
            System.out.println(bd.manhattan());
            System.out.println(bd.toString());


        }

//        System.out.println("Test three twin:");
//        for (int i = 0; i < 3; i++) {
//            System.out.print(initial.toString());
//            System.out.print(initial.twin().toString());
//        }

//        System.out.println("Check equals");
//        Board initial2 = new Board(tiles);
//        Board initialtwin = initial.twin();
//        System.out.println(initial);
//        System.out.println(initial2);
//        System.out.println(initialtwin);
//        System.out.println(initial2.equals(initial));
//        System.out.println(initial2.equals(initialtwin));
//
//
//        System.out.println("Check hamming");
//        System.out.println(initial2);
//        System.out.println(initial2.hamming());
//        System.out.println(initialtwin);
//        System.out.println(initialtwin.hamming());
//
//        int[][] target = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
//        Board targetBoard = new Board(target);
//
//        System.out.println("Check manhattan");
//        System.out.println(initial);
//        System.out.println(initial.manhattan());
//        System.out.println("Check manhattan");
//        System.out.println(initialtwin);
//        System.out.println(initialtwin.manhattan());
//        System.out.println("target");
//        System.out.println(targetBoard.manhattan());
    }

}
