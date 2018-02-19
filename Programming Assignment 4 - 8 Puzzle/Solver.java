/******************************************************************************
 *  Name:    Yao Jiqian
 *  NetID:   JQ
 *  Precept: P04
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  the Solver class.
 * 
 ******************************************************************************/

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
// import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    
    private boolean solved;
    private ManhattanNode currentNode; // current search node
    
    private class ManhattanNode implements Comparable<ManhattanNode> {
        private final Board theBoard;
        private ManhattanNode previous;
        private int moves = 0;
        private final int priority;
        private boolean isInitParity;
        
        public ManhattanNode(Board bd, boolean isInitParity) {
            if (bd == null) 
                throw new NullPointerException("Null Board");          
            theBoard = bd;
            this.moves = 0;
            this.priority = bd.manhattan() + moves;
            this.isInitParity = isInitParity;
        }   
        
        public ManhattanNode(Board bd, ManhattanNode preNode) {
            
            previous = preNode;
            if (preNode == null) {
                moves = 0;
            } else {
                moves = preNode.moves + 1;
                this.isInitParity = previous.isInitParity;
            }
            theBoard = bd;
            priority = bd.manhattan() + moves;
        }
        
               
        
        @Override
        public int compareTo(ManhattanNode that) {

            if (this.priority == that.priority) 
                return this.theBoard.manhattan() - that.theBoard.manhattan();
            else
                return this.priority - that.priority; 
        }
    }
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException("Null Board");
        
        MinPQ<ManhattanNode> theQueue = new MinPQ<ManhattanNode>();
        ManhattanNode initNode = new ManhattanNode(initial, true);
        // ManhattanNode swapNode = new ManhattanNode(initial.twin(), false);
        theQueue.insert(initNode);
        /*
        theQueue.insert(swapNode);
        
        while (true)
        {
            currentNode = theQueue.delMin();
            if (currentNode.theBoard.isGoal()) break;
            for (Board nb : currentNode.theBoard.neighbors())
                if (currentNode.previous == null || 
                    !nb.equals(currentNode.previous.theBoard))
                    theQueue.insert(new ManhattanNode(nb, currentNode));          
        } // only one of the two nodes can lead to the goal board
        solved = currentNode.isInitParity && currentNode.theBoard.isGoal();
        */
        solved = false;
        while (!theQueue.isEmpty()) {
            currentNode = theQueue.delMin();
            // StdOut.println(currentNode.theBoard.toString());
            if (currentNode.theBoard.isGoal()) {
                solved = true;
                break;
            }
            for (Board nb : currentNode.theBoard.neighbors()) {
                ManhattanNode pre = currentNode.previous;
                boolean found = false;
                while (pre != null) {
                    // StdOut.println(pre.theBoard.toString());
                    if (nb.equals(pre.theBoard)) {
                        found = true;
                        break;
                    }
                    pre = pre.previous;
                }
                if (!found) theQueue.insert(new ManhattanNode(nb, currentNode));
            }
        } 

    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return solved;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!solved) return -1;
        return currentNode.moves;
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
 
        Stack<Board> boards = new Stack<Board>();
        ManhattanNode node = currentNode; // another reference
 
        while (node != null) {
            boards.push(node.theBoard);
            node = node.previous;
        }
        return boards;
    }
    
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

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