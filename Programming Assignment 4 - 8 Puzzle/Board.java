/******************************************************************************
 *  Name:    Yao Jiqian
 *  NetID:   JQ
 *  Precept: P04
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  the board class.
 * 
 ******************************************************************************/

import java.util.Iterator;
import java.util.ArrayList;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    
    private final int dim;
    private final int[][] blocks;
    
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blks) {
        dim = blks[0].length;
        blocks = new int[dim][dim];
        
        // copy the blocks to private variable.
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                blocks[i][j] = blks[i][j];
            }
        }
    }
    
    // board dimension n
    public int dimension() {
        return dim;
    }
    
    // number of blocks out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] == 0) {
                    continue;
                } else if (blocks[i][j] != (i * dim) + j + 1) {
                    count++;
                }
            }
        }
        return count;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int count = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] == 0) {
                    continue;
                } else if (blocks[i][j] != (i * dim) + j + 1) {
                    int shouldI = (blocks[i][j] -1) / dim;
                    int shouldJ = (blocks[i][j] -1) % dim;
                    count += (Math.abs(shouldI - i) + Math.abs(shouldJ - j));
                }
            }
        }
        return count;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        
        return hamming() == 0;
    }
    
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] twinBlocks = new int[dim][dim];
        // copy the blocks to private variable.
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                twinBlocks[i][j] = blocks[i][j];
            }
        }
        if (blocks[0][0] != 0 && blocks[0][1] != 0) {
            int temp = twinBlocks[0][0];
            twinBlocks[0][0] = twinBlocks[0][1];
            twinBlocks[0][1] = temp;
        } else {
            int temp = twinBlocks[dim-1][dim-1];
            twinBlocks[dim-1][dim-1] = twinBlocks[dim-1][dim-2];
            twinBlocks[dim-1][dim-2] = temp;
        }
        return new Board(twinBlocks);
    }
    
    // does this board equal y?
    public boolean equals(Object y) {
        
        if (this == y) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        // if (that == null) {
        //     return false;
        // }
        if (that.dimension() != dim) return false;
        
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<Board>();
        ArrayList<Integer> possibleSwitchs = null;
        // int currentPos = 0;
        int zeroI = 0;
        int zeroJ = 0;
        
        boolean found = false;
        for (zeroI = 0; zeroI < dim; zeroI++) {
            for (zeroJ = 0; zeroJ < dim; zeroJ++) {
                if (blocks[zeroI][zeroJ] == 0) {
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }

        possibleSwitchs = new ArrayList<Integer>();
        if (zeroI > 0) possibleSwitchs.add(0);
        if (zeroI < (dim - 1)) possibleSwitchs.add(1);
        if (zeroJ > 0) possibleSwitchs.add(2);
        if (zeroJ < (dim - 1)) possibleSwitchs.add(3);
        
        for (int k = 0; k < possibleSwitchs.size(); k++) {
            int[][] blks = new int[dim][dim];
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    blks[i][j] = blocks[i][j];
                }
            }
            int switchI = zeroI;
            int switchJ = zeroJ;
            // StdOut.printf("currentPos = %d\n", currentPos);
            // StdOut.println(toString());
            int currentSwitch = possibleSwitchs.get(k);
            switch(currentSwitch) {
                case 0:
                    switchI--;
                    break;
                case 1:
                    switchI++;
                    break;
                case 2:
                    switchJ--;
                    break;
                case 3:
                    switchJ++;
                    break;
                default:
                    
            }
            // currentPos++;
            
            if (zeroI != switchI || zeroJ != switchJ) {
                blks[zeroI][zeroJ] = blks[switchI][switchJ];
                blks[switchI][switchJ] = 0;
            }
            neighbors.push(new Board(blks));
        }
        
        return neighbors;
    }
    
    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder sb  =  new StringBuilder();
        sb.append(dim);
        sb.append('\n');
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] < 10) sb.append(' ');
                // sb.append(' ');
                sb.append(blocks[i][j]);
                sb.append(' ');
            }
            // sb.deleteCharAt(sb.length() -1); //remove last ' '
            sb.append('\n');
        }
        // sb.deleteCharAt(sb.length() -1); //remove last '\n'
        return sb.toString();
    }
    
    /*
    private class BoardIterator implements Iterator<Board> {
        // enum SwitchPos {UP, DOWN, LEFT, RIGHT, NO_MOVE};
        // 0:UP, 1:DOWN, 2:LEFT, 3:RIGHT, -1:NO_MOVE
        private ArrayList<Integer> possibleSwitchs = null;
        private int currentPos = 0;
        private int zeroI;
        private int zeroJ;

        
        public BoardIterator() {
            
            boolean found = false;
            for (zeroI = 0; zeroI < dim; zeroI++) {
                for (zeroJ = 0; zeroJ < dim; zeroJ++) {
                    if (blocks[zeroI][zeroJ] == 0) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }

            possibleSwitchs = new ArrayList<Integer>();
            if (zeroI > 0) possibleSwitchs.add(0);
            if (zeroI < (dim - 1)) possibleSwitchs.add(1);
            if (zeroJ > 0) possibleSwitchs.add(2);
            if (zeroJ < (dim - 1)) possibleSwitchs.add(3);
        }
        
        @Override
        public boolean hasNext() {
            
            return currentPos < possibleSwitchs.size();
        }
        
        @Override
        public Board next() {
            
            int currentSwitch = possibleSwitchs.get(currentPos);
            currentPos++;
            // while(currentPos < 4){
            //     if (possibleSwitchs.get(currentPos) != -1) {
            //         currentSwitch = possibleSwitchs[currentPos];
            //         break;
            //     }
            //     currentPos++;
            // }
            int[][] blks = new int[dim][dim];
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    blks[i][j] = blocks[i][j];
                }
            }
            int switchI = zeroI;
            int switchJ = zeroJ;
            // StdOut.printf("currentPos = %d\n", currentPos);
            // StdOut.println(toString());
            
            switch(currentSwitch) {
                case 0:
                    switchI--;
                    break;
                case 1:
                    switchI++;
                    break;
                case 2:
                    switchJ--;
                    break;
                case 3:
                    switchJ++;
                    break;
                default:
                    
            }
            //currentPos++;
            
            if (zeroI != switchI || zeroJ != switchJ) {
                blks[zeroI][zeroJ] = blks[switchI][switchJ];
                blks[switchI][switchJ] = 0;
            }
            return new Board(blks);
        }
        
        @Override
        public void remove() {
        }
    }
    */
    // unit tests (not graded)
    public static void main(String[] args) {
        
        int[][] b = new int[][] { {1, 2, 3}, {4, 5, 0}, {7, 8, 6} };
        
        Board board = new Board(b);
        StdOut.println(board.toString());
        StdOut.println(board.hamming());
        StdOut.println(board.manhattan());
        StdOut.println();
        Iterator<Board> ni = board.neighbors().iterator();
        while (ni.hasNext()) {
            StdOut.println(ni.next().toString());
            StdOut.println();
        }
    }
}