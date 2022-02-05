import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Solver {
    // find a solution to the initial board (using the A* algorithm)

    private static class BoardKey implements Comparable<BoardKey> {
        private final Board bd;
        private final int man;
        private final int priority;
        private final BoardKey parent;
        private final int moves;
        private final boolean twin;

        public BoardKey(Board board, boolean twin) {
            bd = board;  //
            parent = null;
            this.twin = twin;
            moves = 0;
            man = board.manhattan();
            priority = man + moves;
        }

        public BoardKey(Board board, BoardKey parent) {
            bd = board;
            this.parent = parent;
            twin = parent.twin;
            moves = parent.moves + 1;
            man = board.manhattan();
            priority = man + moves;

        }

        public Board getBoard() {
            return bd;
        }

        public BoardKey getParent() {
            return parent;
        }

        public boolean isTwin() {
            return twin;
        }

        public int compareTo(BoardKey o) {

            if (this.priority == o.priority) {
                return Integer.compare(this.man, o.man);
            } else {
                return Integer.compare(this.priority, o.priority);
            }
        }

        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (this == o) {
                return true;
            }

            if (o.getClass() != this.getClass()) {
                return false;
            }

            BoardKey temp = (BoardKey) o;
            return getBoard().equals(temp.getBoard());
        }

    }

    private List<Board> solveBd;
    private final boolean solvable;
    private final int move;
    private final Board initial;

    public Solver(Board initBd) {

        if (initBd == null) {
            throw new IllegalArgumentException();
        }
        this.initial = initBd;

        solveBd = new ArrayList<Board>();
        MinPQ<BoardKey> bdKeyPq = new MinPQ<BoardKey>();

        bdKeyPq.insert(new BoardKey(initial, false));
        bdKeyPq.insert(new BoardKey(initial.twin(), true));

        BoardKey curBdKey = bdKeyPq.delMin();
        Board curBd = curBdKey.getBoard();

        while (!curBd.isGoal()) {
            for (Board nbBd : curBd.neighbors()) {
                if (curBdKey.getParent() == null || !nbBd.equals(curBdKey.getParent().getBoard())) {
                    bdKeyPq.insert(new BoardKey(nbBd, curBdKey));
                }
            }
            curBdKey = bdKeyPq.delMin();
            curBd = curBdKey.getBoard();
        }

        solvable = !curBdKey.isTwin();
        if (!solvable) {
            move = -1;
            solveBd = null;
        } else {
            solveBd = new ArrayList<>();
            while (curBdKey != null) {
                solveBd.add(curBdKey.getBoard());
                curBdKey = curBdKey.getParent();
            }
            move = solveBd.size() - 1;
            Collections.reverse(solveBd);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {

        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        else return move;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return (Iterable<Board>) new SolutionIterable(solveBd);
    }

    private class SolutionIterable implements Iterable<Board> {

        List<Board> itBd;

        private SolutionIterable(List<Board> solveBd) {
            itBd = solveBd;
        }

        public Iterator<Board> iterator() {
            return itBd.iterator();
        }
    }

    // test client (see below)
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
        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
