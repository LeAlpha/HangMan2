package com.example.oii.hangman;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class HangManSpil extends AppCompatActivity implements View.OnClickListener {
    Logic gameLogic = new Logic();
    Button goKnap;
    EditText inputFelt;
    TextView wordSoFar, usedLetters;
    ImageView picture;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangmanspil);


        goKnap = findViewById(R.id.GoButton);
        inputFelt = findViewById(R.id.inputField);
        wordSoFar = findViewById(R.id.showWord);
        usedLetters = findViewById(R.id.usedLettersField);
        picture = findViewById(R.id.Picture);

        onReload();

        goKnap.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == goKnap) {

            gameLogic.gætBogstav(inputFelt.getText().toString());
            wordSoFar.setText(gameLogic.getSynligtOrd());
            usedLetters.setText(gameLogic.getBrugteBogstaver());


            updatePicture();
            inputFelt.setText("");


            if(gameLogic.erSpilletSlut() == true){
                AlertDialog.Builder builder = new AlertDialog.Builder(HangManSpil.this);
                builder.setCancelable(false);
                if(gameLogic.erSpilletTabt() == true){
                    builder.setTitle("U DED Boii");
                    builder.setMessage("Ordet var: " + gameLogic.getOrdet() + "\nTryk på knappen for at spille igen");
                    builder.setNegativeButton("Nyt Spil", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            gameLogic.nulstil();
                            onReload();
                            dialog.cancel();
                        }
                    });
                }
                else if(gameLogic.erSpilletVundet() == true){

                    builder.setTitle("Yay! Du vandt! ");
                    builder.setMessage("Tryk på knappen for at spille igen");

                    builder.setPositiveButton("Nyt Spil", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            gameLogic.nulstil();
                            onReload();
                            dialog.cancel();
                        }
                    });
                }
                builder.show();
            }
        }
    }

    public void onReload(){
        wordSoFar.setText(gameLogic.getSynligtOrd());
        picture.setImageResource(R.drawable.galge);
        usedLetters.setText("");
        inputFelt.setText("");
    }

    public void updatePicture(){
        if(gameLogic.getAntalForkerteBogstaver()==1){
            picture.setImageResource(R.drawable.forkert1);
        } else if (gameLogic.getAntalForkerteBogstaver()==2) {
            picture.setImageResource(R.drawable.forkert2);
        } else if (gameLogic.getAntalForkerteBogstaver()==3) {
            picture.setImageResource(R.drawable.forkert3);
        } else if (gameLogic.getAntalForkerteBogstaver()==4) {
            picture.setImageResource(R.drawable.forkert4);
        } else if (gameLogic.getAntalForkerteBogstaver()==5) {
            picture.setImageResource(R.drawable.forkert5);
        } else if (gameLogic.getAntalForkerteBogstaver()==6) {
            picture.setImageResource(R.drawable.forkert6);
        }
    }


}
