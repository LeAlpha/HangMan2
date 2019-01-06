package com.example.oii.hangman;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.HashMap;


public class HangManSpil extends AppCompatActivity implements View.OnClickListener {
    Logic gameLogic = new Logic();
    Button nyeOrd, ordlisteknap;
    Button q, w, e, r, t, y, u, i, o, p, å, a, s, d, f, g, h, j, k, l, æ, ø, z, x, c, v, b, n, m;

    TextView wordSoFar, usedLetters;
    ImageView picture;
    SharedPreferences pm;
    String input;
    ArrayList<Button> keyboard = new ArrayList();
    SoundPool soundplayer;
    private HashMap sounds = new HashMap();
    LottieAnimationView fireworks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangmanspil);
        SoundpoolPlayer(this);

        fireworks = findViewById(R.id.fireworksView);
        nyeOrd = findViewById(R.id.newWords);
        wordSoFar = findViewById(R.id.showWord);
        usedLetters = findViewById(R.id.usedLettersField);
        picture = findViewById(R.id.Picture);
        ordlisteknap = findViewById(R.id.wordlistbtn);


        giveButtonID();
        addButtonToArray();

        onReload();
        for (Button bn : keyboard) {
            bn.setOnClickListener(this);
        }

        nyeOrd.setOnClickListener(this);
        ordlisteknap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        class async extends AsyncTask {

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

        if(keyboard.contains(v)){
            int index = returnButtonIndex(v);
            keyboard.get(index).setEnabled(false);
            input = keyboard.get(index).getText().toString().toLowerCase();


            gameLogic.gætBogstav(input);
            wordSoFar.setText(gameLogic.getSynligtOrd());
            usedLetters.setText(gameLogic.getBrugteBogstaver());
            updatePicture();


            if (gameLogic.erSpilletSlut() == true) {
                if (gameLogic.erSpilletTabt() == true) {
                    soundplayer.play(2, 0.99f, 099f, 0, 0 , 1);

                    alertboxLost();
                } else if (gameLogic.erSpilletVundet() == true) {
                    soundplayer.play(3, 0.99f, 099f, 0, 0 , 1);
                    alertboxWon();
                }
            }
        }


        else if (v == nyeOrd) {
            new async().execute();

        }
        else if (v == ordlisteknap){
            Intent browser = new Intent(this, WordsToGuess.class);
            browser.putExtra("arraylist", gameLogic.muligeOrd);
            startActivityForResult(browser, 1);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                int result = data.getIntExtra("position", 0);
                Toast.makeText(HangManSpil.this, "no breaks" + " " + result, Toast.LENGTH_SHORT).show();
                gameLogic.nulstilBestemt(result);
                onReload();
            }
        }

    }

    public void onReload(){
        soundplayer.play(1, 0.99f, 099f, 0, 0 , 1);
        wordSoFar.setText(gameLogic.getSynligtOrd());
        picture.setImageResource(R.drawable.galge);
        usedLetters.setText("");
        for(Button bt : keyboard) {
            bt.setEnabled(true);
           // bt.setBackground();

        }
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
        fireworks.playAnimation();
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
        fireworks.cancelAnimation();
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
    public void addButtonToArray(){
        keyboard.add(this.q);
        keyboard.add(this.w);
        keyboard.add(this.e);
        keyboard.add(this.r);
        keyboard.add(this.t);
        keyboard.add(this.y);
        keyboard.add(this.u);
        keyboard.add(this.i);
        keyboard.add(this.o);
        keyboard.add(this.p);
        keyboard.add(this.å);
        keyboard.add(this.a);
        keyboard.add(this.s);
        keyboard.add(this.d);
        keyboard.add(this.f);
        keyboard.add(this.g);
        keyboard.add(this.h);
        keyboard.add(this.j);
        keyboard.add(this.k);
        keyboard.add(this.l);
        keyboard.add(this.æ);
        keyboard.add(this.ø);
        keyboard.add(this.z);
        keyboard.add(this.x);
        keyboard.add(this.c);
        keyboard.add(this.v);
        keyboard.add(this.b);
        keyboard.add(this.n);
        keyboard.add(this.m);

    }
    public void giveButtonID(){
        this.q = findViewById(R.id.buttonQ);
        this.w = findViewById(R.id.buttonW);
        this.e = findViewById(R.id.buttonE);
        this.r = findViewById(R.id.buttonR);
        this.t = findViewById(R.id.buttonT);
        this.y = findViewById(R.id.buttonY);
        this.u = findViewById(R.id.buttonU);
        this.i = findViewById(R.id.buttonI);
        this.o = findViewById(R.id.buttonO);
        this.p = findViewById(R.id.buttonP);
        this.å = findViewById(R.id.buttonAA);
        this.a = findViewById(R.id.buttonA);
        this.s = findViewById(R.id.buttonS);
        this.d = findViewById(R.id.buttonD);
        this.f = findViewById(R.id.buttonF);
        this.g = findViewById(R.id.buttonG);
        this.h = findViewById(R.id.buttonH);
        this.j = findViewById(R.id.buttonJ);
        this.k = findViewById(R.id.buttonK);
        this.l = findViewById(R.id.buttonL);
        this.æ = findViewById(R.id.buttonAE);
        this.ø = findViewById(R.id.buttonEO);
        this.z = findViewById(R.id.buttonZ);
        this.x = findViewById(R.id.buttonX);
        this.c = findViewById(R.id.buttonC);
        this.v = findViewById(R.id.buttonV);
        this.b = findViewById(R.id.buttonB);
        this.n = findViewById(R.id.buttonN);
        this.m = findViewById(R.id.buttonM);


    }
    public int returnButtonIndex(View v){

    for(int i = 0; i < keyboard.size(); i++)
        if(v == keyboard.get(i))
            return i;

        return 99;
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
    public void SoundpoolPlayer(Context pcontext){
        this.soundplayer = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        sounds.put(R.raw.sm64_key_get, this.soundplayer.load(pcontext, R.raw.sm64_key_get, 1));
        sounds.put(R.raw.smb_mariodie, this.soundplayer.load(pcontext, R.raw.smb_mariodie, 1));
        sounds.put(R.raw.smb_powerup, this.soundplayer.load(pcontext, R.raw.smb_powerup, 1));

    }
}

