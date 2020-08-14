package com.example.android.islandspainter;

public class Cell {
    private int index, islandID;
    private CellStatus stat = CellStatus.WHITE;


    public Cell(int index) {
        this.index = index;
    }
}
