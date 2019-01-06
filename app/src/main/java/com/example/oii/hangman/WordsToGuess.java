package com.example.oii.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(WordsToGuess.this, "Lez go" + " the position is : " + position, Toast.LENGTH_SHORT).show();

            }
        });



    }
}
