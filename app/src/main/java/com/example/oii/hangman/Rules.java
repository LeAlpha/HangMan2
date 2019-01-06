package com.example.oii.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Rules extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);


        TextView rules = findViewById(R.id.ruleTextView);
        rules.setText("Reglerne er som følger: \n" +
                "Bogstaver skal gættes fra en liste af predefinerede ord. For hvert forkert bogstav vil en del af hangman blive tilføjet til galgen. Rigtige bogstaver bliver tilføjet til listen. \n \n"+
                "Man vinder ved at gætte alle bogstaverne i ordet. Hvis man fortsat gætter forkert vil hangman dø, og spillet er tabt" +
                "\n\n\n" +
                "Det er yderligere muligt at komme på highscore listen, ved at vinde spil, og få point. " +
                "Yderligere er det muligt at se den fuldendte liste af ord som kan gættes. På listen er det muligt at vælge et ord som skal gættes. Dette kan bruges til hvis man gerne vil udfordre en anden til spillet. Afsluttende er det også muligt at hente nye ord, fra DR.dk ");
    }
}
