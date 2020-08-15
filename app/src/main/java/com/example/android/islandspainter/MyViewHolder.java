package com.example.android.islandspainter;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
    TextView cell;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        this.cell = itemView.findViewById(R.id.cell);
        cell.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        v.setBackgroundColor(Color.BLACK);

    }
}
