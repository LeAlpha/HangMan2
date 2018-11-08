package com.example.oii.hangman;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Highscore extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences pf;
    TextView tv;
    Button update;
    String hs1_name, hs2_name, hs3_name, hs1_word, hs2_word, hs3_word;
    int hs1_value, hs2_value, hs3_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);


        pf = PreferenceManager.getDefaultSharedPreferences(this);

        tv = findViewById(R.id.textfield_HighScore);
        tv.setText(highscoreText());


        update = (Button) findViewById(R.id.updateButton);
        update.setOnClickListener(this);
    }

    public String highscoreText(){
        loadHighScore();
        return "1. \t"+ hs1_name + "\t " + hs1_value + "\t" + hs1_word +"\n" +
                "2. \t"+ hs2_name +"\t " + hs2_value + "\t" + hs2_word + "\n" +
                "3. \t"+ hs3_name + "\t " + hs3_value + "\t" + hs3_word;


    }



    public void loadHighScore() {
    }


    @Override
    public void onClick(View v) {
        if(v == update){    highscoreText();}

    }
}
