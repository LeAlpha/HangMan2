package com.example.oii.hangman;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Highscore extends AppCompatActivity {

    SharedPreferences pf;
    TextView tv;
    String hs1_name, hs2_name, hs3_name, hs1_word, hs2_word, hs3_word;
    int hs1_value, hs2_value, hs3_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);


        pf = (SharedPreferences) PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pf.edit();

        tv = findViewById(R.id.textfield_HighScore);
        tv.setText(highscoreText());
    }

    public String highscoreText(){
        loadHighScore();
        return "1. \t"+ hs1_name + "\t " + hs1_value + "\t" + hs1_word +"\n" +
                "2. \t"+ hs2_name +"\t " + hs2_value + "\t" + hs2_word + "\n" +
                "3. \t"+ hs3_name + "\t " + hs3_value + "\t" + hs3_word;


    }



    public String loadHighScore(){
        hs1_name = pf.getString(getString(R.string.H1_name), "Navn 1");
        hs2_name = pf.getString(getString(R.string.H1_name), "Navn 2");
        hs3_name = pf.getString(getString(R.string.H1_name), "Navn 3");
        hs1_word = pf.getString("Word", "Ord1");
        hs2_word = pf.getString("Word", "Ord2");
        hs3_word = pf.getString("Word", "Ord3");
        hs1_value = pf.getInt(String.valueOf(R.integer.H1_Score), 0);
        hs2_value = pf.getInt(String.valueOf(R.integer.H2_Score), 0);
        hs3_value = pf.getInt(String.valueOf(R.integer.H3_Score), 0);
        return null;
    }
}
