package com.example.android.islandspainter;

public class Board {
    private int rows, cols, size;
    private int numOfIslands = 0;
    private Cell[] board;

    private boolean clean = true;
    private boolean solved = false;

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
}
