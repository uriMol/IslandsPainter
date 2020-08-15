package com.example.android.islandspainter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BoardActivity extends AppCompatActivity implements View.OnClickListener{

    int cols, rows;
    TextView tvNumOfIslands;
    Button random, clean, solve;
    Board board;
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        setUIViews();
        createBoard(getIntent());
        createRecycleView();
    }

    private void createRecycleView() {
        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new GridLayoutManager(this, cols);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(board);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void createBoard(Intent incomingIntent) {
        rows = incomingIntent.getIntArrayExtra("dimension")[0];
        cols = incomingIntent.getIntArrayExtra("dimension")[1];
        board = new Board(rows, cols);

    }

    private void setUIViews() {
        tvNumOfIslands = findViewById(R.id.tvNumOfIslands);
        random = findViewById(R.id.btRandom);
        random.setOnClickListener(this);
        clean = findViewById(R.id.btClean);
        clean.setOnClickListener(this);
        solve = findViewById(R.id.btSolve);
        solve.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String tag = v.getTag().toString();
        switch(tag){
            case("random"):
                board.randomize();
                break;
            case("solve"):
                board.solve();
                Toast.makeText(this, "Map is solved!", Toast.LENGTH_SHORT).show();
                break;
            case("clean"):
                //board.cleanBoard();
                break;
        }
    }
}
