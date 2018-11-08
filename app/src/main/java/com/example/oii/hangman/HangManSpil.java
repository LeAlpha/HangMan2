package com.example.oii.hangman;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangmanspil);


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
                if(gameLogic.erSpilletTabt() == true){
                    alertboxLost();
                }
                else if(gameLogic.erSpilletVundet() == true){
                    alertboxWon();
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

    public void alertboxLost(){
        AlertDialog.Builder builder = new AlertDialog.Builder(HangManSpil.this);
        builder.setCancelable(false);

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

    public void alertboxWon(){

        AlertDialog.Builder builder = new AlertDialog.Builder(HangManSpil.this);
        builder.setCancelable(false);
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
        if(calculateHighscorePosition(gameLogic.generateHighScore())!=4){
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setTitle("Du kom på highscore listen! Skriv dit navn!");

            final EditText input = new EditText(this);

            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder2.setView(input);


            builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = input.getText().toString();
                    putNewHighScore(name);
                }
            });
            builder2.show();

        }


    }




    public void putNewHighScore(String name){
        pm = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor myEditor = pm.edit();
        int position = calculateHighscorePosition(gameLogic.generateHighScore());
        System.out.println("HER!! "+position);

        if(position == 1){
            myEditor.putString("H3Name" , pm.getString("H2Name", ""));
            myEditor.putString("H2Name" , pm.getString("H1Name", ""));
            myEditor.putString("H1Name" , name );


            myEditor.putString("H3Word" , pm.getString("H2Word", ""));
            myEditor.putString("H2Word" , pm.getString("H1Word", ""));
            myEditor.putString("H1Word" , gameLogic.getOrdet() );

            myEditor.putInt("H3Score", pm.getInt("H2Score", 0));
            myEditor.putInt("H2Score", pm.getInt("H1Score", 0));
            myEditor.putInt("H1Score", gameLogic.generateHighScore());

        }
        else if(position== 2){
            myEditor.putString("H3Name" , pm.getString("H2Name", ""));
            myEditor.putString("H2Name" , name );

            myEditor.putString("H3Word" , pm.getString("H2Word", ""));
            myEditor.putString("H2Word" , gameLogic.getOrdet() );

            myEditor.putInt("H3Score", pm.getInt("H2Score", 0));
            myEditor.putInt("H2Score", gameLogic.generateHighScore());
        }
        else if(position== 3){
            myEditor.putString("H3Name" , name);

            myEditor.putString("H3Word" , gameLogic.getOrdet());

            myEditor.putInt("H3Score", gameLogic.generateHighScore());
        }
        else if(position== 4){

        }

        myEditor.commit();

        /*
        myEditor.putInt("H1Score", gameLogic.generateHighScore());
        myEditor.putString("H2Name", "Navn2");
        myEditor.putString("H1Word", "Still vorking!");
        myEditor.commit();
*/

    }


 public int calculateHighscorePosition(int score){
     pm = PreferenceManager.getDefaultSharedPreferences(this);
     int hs1 = pm.getInt("H1Score", 0);
     int hs2 = pm.getInt("H2Score", 0);
     int hs3 = pm.getInt("H3Score", 0);


        if(score > hs1){ return 1; }
        else if (score >hs2){return 2;}
        else if(score > hs3){ return 3;}
        else{return 4;}


 }
}
