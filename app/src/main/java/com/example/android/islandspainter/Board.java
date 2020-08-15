package com.example.android.islandspainter;

import android.util.Log;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Random;

public class Board {
    private int rows, cols, size;
    private int numOfIslands = 0;
    private Cell[] board;

    private boolean clean = true;
    private boolean solved = false;

    private static final boolean INCLUDE_DIAG = false;

    final double FREQUENCY = 0.4;

    public Board(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.size = rows*cols;
        this.board = new Cell[rows*cols];
        for(int i = 0; i < size; i++ ){
            board[i] = new Cell(i);
        }
    }


    public int getSize() {
        return this.size;
    }

    public void randomize() {
        Random rand = new Random();
        for(Cell cell:this.board){
            if(rand.nextDouble() < FREQUENCY){
                cell.stat = CellStatus.BLACK;
            } else{
                cell.stat = CellStatus.WHITE;
            }
        }
    }

    public Cell[] getBoard() {
        return this.board;
    }

    /*
        Solves the board - if board isn't solved - iterates
        on it's cells and if necessary activates paintIsland()
     */
    public void solve() {
        if(!solved){
            for(Cell cell:board){
                if(cell.getStat() == CellStatus.BLACK){
                    PaintIsland(cell);
                }
            }
            solved = true;
        }
    }

    /*
        On each call - the function paints the entire island.
        PaintIsland uses a stack to store all relevant
        neighbors cell that are part of the island and should
        be painted. Very similar to BFS algorithm
     */
    private void PaintIsland(Cell cell) {
        numOfIslands++;
        LinkedList<Integer[]> stack = new LinkedList<Integer[]>();
        int i = cell.getRow();
        int j = cell.getCol();
        stack.add(new Integer[]{i, j});
        Integer[] indexes;
        while(!stack.isEmpty()){
            indexes = stack.remove();
            i = indexes[0];
            j = indexes[1];
            board[i*cols + j].setIslandID(numOfIslands);
            board[i*cols + j].setStat(CellStatus.COLORED);
            if ((INCLUDE_DIAG)) {
                addNeighborsToStackDiag(indexes, stack);
            } else {
                addNeighborsToStackNoDiag(indexes, stack);
            }
        }
    }

    private void addNeighborsToStackDiag(Integer[] indexes, LinkedList<Integer[]> stack) {
        int i = indexes[0];
        int j = indexes[1];
        for(int k = Math.max(0,i-1); k < Math.min(rows, i + 2); k++){
            for(int l = Math.max(0, j - 1); l < Math.min(cols, j + 2); l++){
                if(board[k*cols + l].getStat() == CellStatus.BLACK && (i != k || j != l)){
                    stack.add(new Integer[]{k, l});
                }
            }
        }
    }

    private void addNeighborsToStackNoDiag(Integer[] indexes, LinkedList<Integer[]> stack) {
        int i = indexes[0];
        int j = indexes[1];
        if(i > 0 && board[(i-1)*cols + j].getStat() == CellStatus.BLACK){
            stack.add(new Integer[]{i-1, j});
        }
        if(i < rows - 1 && board[(i+1)*cols + j].getStat() == CellStatus.BLACK){
            stack.add(new Integer[]{i+1, j});
        }
        if(j > 0 && board[i*cols + j-1].getStat() == CellStatus.BLACK){
            stack.add(new Integer[]{i, j-1});
        }
        if(j < cols - 1 && board[i*cols + j+1].getStat() == CellStatus.BLACK){
            stack.add(new Integer[]{i, j+1});
        }
    }


    public class Cell {
        private int index, islandID;
        private CellStatus stat = CellStatus.WHITE;


        public Cell(int index) {
            this.index = index;
        }

        public CellStatus getStat(){
            return this.stat;
        }

        public int getIslandID() {
            return islandID;
        }

        public int getRow() {
            return index/cols;
        }

        public int getCol() {
            return index%cols;
        }

        public void setIslandID(int ID) {
            this.islandID = ID;
        }

        public void setStat(CellStatus status) {
            this.stat = status;
        }
    }
}
