package com.example.oii.hangman;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;

public class Highscore extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences pm;
    TextView tv;
    Button update;
    String hs1_name, hs2_name, hs3_name, hs1_word, hs2_word, hs3_word;
    int hs1_value, hs2_value, hs3_value;

    KonfettiView konfettiView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);



        tv = findViewById(R.id.textfield_HighScore);
        tv.setText(highscoreText());


        update = (Button) findViewById(R.id.updateButton);
        update.setOnClickListener(this);
    }

    public String highscoreText(){
        loadHighScore();
        return "1. \t"+ hs1_name + "\t " + hs1_value + "\t" + hs1_word +"\n"
                +"2. \t"+ hs2_name +"\t " + hs2_value + "\t" + hs2_word + "\n" +
                "3. \t"+ hs3_name + "\t " + hs3_value + "\t" + hs3_word;


    }



    public void loadHighScore() {
        pm = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor myEditor = pm.edit();
        hs1_name = pm.getString("H1Name", "blankName");
        hs1_word = pm.getString("H1Word", "BlankWord");
        hs1_value = pm.getInt("H1Score", 000);

        hs2_name = pm.getString("H2Name", "blankName");
        hs2_word = pm.getString("H2Word", "BlankWord");
        hs2_value = pm.getInt("H2Score", 000);

        hs3_name = pm.getString("H3Name", "blankName");
        hs3_word = pm.getString("H3Word", "BlankWord");
        hs3_value = pm.getInt("H3Score", 000);
    }


    @Override
    public void onClick(View v) {
        if(v == update){finish();}

    }
}
