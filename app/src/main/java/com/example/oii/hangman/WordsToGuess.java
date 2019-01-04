package com.example.oii.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class WordsToGuess extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_to_guess);

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> wordList = bundle.getStringArrayList("arraylist");



        String[] wordlistString = wordList.toArray(new String[0]);
        ArrayAdapter AA = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, wordlistString);
        ListView lw = findViewById(R.id.LW);
        lw.setAdapter(AA);



    }
}
