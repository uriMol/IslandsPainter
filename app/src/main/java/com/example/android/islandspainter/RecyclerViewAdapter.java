package com.example.android.islandspainter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener {

    Board board;

    public RecyclerViewAdapter(Board board) {
        this.board = board;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_view, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    /*
        onBind paints the cell if necessary, sets a reference
        to the cells index using the tag, and set the onClickListener
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        paint(holder, position);
        holder.cell.setTag(position);
        holder.cell.setOnClickListener(this);
    }

    private void paint(MyViewHolder holder, int position) {
        Board.Cell cell = board.getBoard()[position];
        CellStatus stat = cell.getStat();
        if(stat == CellStatus.BLACK){
            holder.cell.setBackgroundColor(Color.BLACK);
        } else if(stat == CellStatus.COLORED){
            randomPaint(cell, holder);
        } else{
            holder.cell.setBackgroundColor(Color.WHITE);
        }

    }

    /*
        RandomPaint is used if the status of the cell is
        colored, and the painting is not really random, its
        the same color for every cell in the island
     */
    private void randomPaint(Board.Cell cell, MyViewHolder holder) {
        int randColor = 31 * cell.getIslandID();
        holder.cell.setBackgroundColor(Color.argb(255, randColor%235 , (2*randColor)%235 , (3*randColor)%235));
    }

    @Override
    public int getItemCount() {
        return board.getSize();
    }

    @Override
    public void onClick(View v) {
        Board.Cell cell = board.getBoard()[(int)v.getTag()];
        if(cell.getStat() == CellStatus.WHITE){
            v.setBackgroundColor(Color.BLACK);
            cell.setStat(CellStatus.BLACK);
            board.unClean();
            board.unSolve();
        }

    }
}
