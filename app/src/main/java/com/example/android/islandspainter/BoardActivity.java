package com.example.android.islandspainter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
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

    /*
        Generating the recycler view and its adapter
        that is using the data from the board
     */
    private void createRecycleView() {
        recyclerView = findViewById(R.id.recycleView);
        layoutManager = new GridLayoutManager(this, cols);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(board);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    /*
        Creates the board - the Data for the board that
        is used by the RecyclerView Adapter
     */
    private void createBoard(Intent incomingIntent) {
        rows = incomingIntent.getIntArrayExtra("dimension")[0];
        cols = incomingIntent.getIntArrayExtra("dimension")[1];
        board = new Board(rows, cols, this);

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
                break;
            case("clean"):
                board.cleanBoard();
                break;
        }
        refreshNumOfIslands();
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private void refreshNumOfIslands() {
        int numOfIslands = board.getNumOfIslands();
        String num = "";
        if ((numOfIslands == 0)) {
            num = "-";
        } else {
            num = "" + numOfIslands;
        }
        tvNumOfIslands.setText("No. of Islands:" + num);
    }


    /*
    Taking care of the onSaveInstanceState -
    We're saving a boolean "isWhite" variable for each cell,
    and saving the "solved" field of the board
     */
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        for(Board.Cell cell:board.getBoard()){
            savedInstanceState.putBoolean(Integer.toString(cell.getIndex()), cell.getStat()!=CellStatus.WHITE);
        }
        savedInstanceState.putInt("numOfIslands", board.getNumOfIslands());
        savedInstanceState.putBoolean("solved", board.isSolved());
    }

    /*
        On restore - we make sure that every "not white" cell
        is painted in black, and if the "solved" field was true
        prior to onSaveInstanceState - we solve the board
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        for(Board.Cell cell:board.getBoard()) {
            if (savedInstanceState.getBoolean(Integer.toString(cell.getIndex()))) {
                cell.setStat(CellStatus.BLACK);
                board.unClean();
            }
        }
        if(savedInstanceState.getBoolean("solved")){
            board.solve();
        }
        board.setNumOfIslands(savedInstanceState.getInt("numOfIslands"));
        refreshNumOfIslands();
        recyclerViewAdapter.notifyDataSetChanged();
    }


}


