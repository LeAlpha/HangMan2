package com.example.oii.hangman;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class Highscore extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences pm;
    Button update;


    private ArrayList<String> word = new ArrayList<>();
    private ArrayList<Integer> score = new ArrayList<>();
    private ArrayList<String> player = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        loadHighScore();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerviewAdapter adapter = new RecyclerviewAdapter(word, score, player, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        update = (Button) findViewById(R.id.updateButton);
        update.setOnClickListener(this);
    }





    public void loadHighScore() {
        pm = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor myEditor = pm.edit();

        word.add(pm.getString("H1Word", "BlankWord"));
        word.add(pm.getString("H2Word", "BlankWord"));
        word.add(pm.getString("H3Word", "BlankWord"));

        score.add(pm.getInt("H1Score", 000));
        score.add(pm.getInt("H2Score", 000));
        score.add(pm.getInt("H3Score", 000));

        player.add(pm.getString("H1Name", "blankName"));
        player.add(pm.getString("H2Name", "blankName"));
        player.add(pm.getString("H3Name", "blankName"));


    }


    @Override
    public void onClick(View v) {
        if(v == update){finish();}

    }
}
