package com.example.oii.hangman;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class HangManSpil extends AppCompatActivity implements View.OnClickListener {
    Logic gameLogic = new Logic();
    Button goKnap, nyeOrd;
    EditText inputFelt;
    TextView wordSoFar, usedLetters;
    ImageView picture;
    SharedPreferences pm;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangmanspil);
        pm = (SharedPreferences) PreferenceManager.getDefaultSharedPreferences(this);


        goKnap = findViewById(R.id.GoButton);
        nyeOrd = findViewById(R.id.newWords);
        inputFelt = findViewById(R.id.inputField);
        wordSoFar = findViewById(R.id.showWord);
        usedLetters = findViewById(R.id.usedLettersField);
        picture = findViewById(R.id.Picture);

        onReload();

        goKnap.setOnClickListener(this);
        nyeOrd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


        class async extends AsyncTask{

            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    nyeOrd.setText("henter");
                    gameLogic.hentOrdFraDr();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        @Override
        protected void onPostExecute(Object result) {
                nyeOrd.setText("Nye ord");
                gameLogic.nulstil();
                onReload();
            Toast.makeText(HangManSpil.this, "Nye ord er hentet :) ", Toast.LENGTH_SHORT).show();
            }
        }


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
                    builder.setTitle("U DED ");
                    builder.setMessage("Ordet var: " + gameLogic.getOrdet() + "\nTryk på knappen for at spille igen");
                    builder.setNegativeButton("Nyt Spil", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            gameLogic.nulstil();
                            onReload();
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
                else if(gameLogic.erSpilletVundet() == true){

                    builder.setTitle("Yay! Du vandt! Ordet var:  " + gameLogic.getOrdet() +" Score "+gameLogic.generateHighScore());
                    builder.setMessage("Tryk på knappen for at spille igen");

                    builder.setPositiveButton("Nyt Spil", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            gameLogic.nulstil();
                            onReload();
                            dialog.cancel();

                        }
                    });
                    builder.show();

                }

            }
        }

        else if(v == nyeOrd){
            new async().execute();

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


    public void setNewScore(String name, int score){

        editor = pm.edit();
        int position = 0;
        if(position == 1){
            editor.putString(getString(R.string.H3_name), getString(R.string.H2_name));
            editor.putString(getString(R.string.H2_name), "H.1");
            editor.putString(getString(R.string.H1_name), name);

            editor.putString("H3_Score", "H2_Word");
            editor.putString("H2_Score", getString(R.string.H1_Word));
            editor.putString("H1_Score", gameLogic.getOrdet());


            editor.putInt((String.valueOf(R.integer.H3_Score)), R.integer.H2_Score);
            editor.putInt((String.valueOf(R.integer.H2_Score)), R.integer.H1_Score);
            editor.putInt((String.valueOf(R.integer.H1_Score)), score);

        } else if(position == 2){

            editor.putString(getString(R.string.H3_name), getString(R.string.H2_name));
            editor.putString(getString(R.string.H2_name), name);

            editor.putInt((String.valueOf(R.integer.H3_Score)), R.integer.H2_Score);
            editor.putInt((String.valueOf(R.integer.H2_Score)), score);


        } else if(position == 3) {
            editor.putString(getString(R.string.H3_name), name);
            editor.putInt((String.valueOf(R.integer.H3_Score)), score);
        }
    }


}
