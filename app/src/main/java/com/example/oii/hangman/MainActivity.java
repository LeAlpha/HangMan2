package com.example.oii.hangman;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button startSpilKnap, highscoreKnap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startSpilKnap = findViewById(R.id.GoToGameButton);
        highscoreKnap = findViewById(R.id.hsButton);
        startSpilKnap.setOnClickListener(this);
        highscoreKnap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == startSpilKnap) {
            Intent browser = new Intent(this , HangManSpil.class);
            startActivity(browser);
        }
        else if (v == highscoreKnap) {
            Intent browser = new Intent(this , Highscore.class);
            startActivity(browser);
        }
    }
}