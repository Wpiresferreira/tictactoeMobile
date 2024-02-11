package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    int mode; // it stores if 1-player or 2-player mode
    int turn;  // it stores who plays next time
    Integer winner = null; // null=game running, 0=tie, 1=player1 won, 2=player2 won.
    TextView score1, score2, tv_player1, tv_player2, message; // they will be used to change screen content;
    String player1, player2; // store the player's name
    TextView tv_player1Name, tv_player2Name ;
Button bt_Continue;
    Button[] buttons;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    public void goMain(View view) { // change the screen and start the game
        setContentView(R.layout.activity_main);
    }
    public void goGame(View view){ // change the screen and start the game
        setContentView(R.layout.activity_game);

        score1 = findViewById(R.id.tv_Score0);
        score2 = findViewById(R.id.tv_Score2);
        message = findViewById(R.id.tv_Msg);
        tv_player1 = findViewById(R.id.tv_Player1);
        tv_player2 = findViewById(R.id.tv_Player2);

        bt_Continue = findViewById(R.id.bt_Continue);

        buttons = new Button[]{findViewById(R.id.bt_00), findViewById(R.id.bt_01), findViewById(R.id.bt_02),
                findViewById(R.id.bt_10), findViewById(R.id.bt_11), findViewById(R.id.bt_12),
                findViewById(R.id.bt_20), findViewById(R.id.bt_21), findViewById(R.id.bt_22)};

        tv_player1.setText(player1);
        tv_player2.setText(player2);

        StartGame();
    }

    public void goHelp(View view){
        setContentView(R.layout.activity_help);
    }
    public void goChooseNames(View view){
        setContentView(R.layout.activity_playernames);
        Button buttonPressed = (Button) view;
        mode = Integer.parseInt(buttonPressed.getText().toString().substring(0,1));

        tv_player1Name = findViewById(R.id.tv_Player1);
        tv_player2Name = findViewById(R.id.tv_player2);

        if(mode == 1){
            tv_player2Name.setText(R.string.computer);
            tv_player2Name.setVisibility(View.INVISIBLE);
        }

    }
    private void StartGame() {
        for (Button b:buttons){
            b.setClickable(false);
            b.setBackgroundColor(Color.parseColor("#4D000000"));

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            b.startAnimation(animation);

        }

        winner = null;

        Random randomPlayer = new Random();
        turn = randomPlayer.nextInt(2) + 1;


        message.setText(R.string.sorting);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        message.startAnimation(animation);

        String firstMessage;
        if(turn == 1){
            firstMessage = player1.concat(getString(R.string.start));
        }else{
            firstMessage = player2.concat(getString(R.string.start));
        }
        animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        message.startAnimation(animation);

        MessageDelayed(firstMessage);

        Handler delay = new Handler();
        delay.postDelayed(() -> {
            if (mode == 1 && turn == 2){
                ComputerPlay();
            }else{
                for(Button b : buttons){
                    if(b == null){
                        return;
                    }
                    if ( b.getText() == null || b.getText().equals("")){
                        b.setClickable(true);
                    }
                }
            }
        }, 3000);

    }


    public void ClickButton(View view){

        if (winner!=null){
            return;
        }
        String token;
        Button buttonClicked = (Button)view;
        if(turn == 1){
            token = "X";
        }else{
            token = "0";
        }

        buttonClicked.setText(token);
        MediaPlayer pencil= MediaPlayer.create(MainActivity.this,R.raw.pencil);
        pencil.start();
        buttonClicked.setClickable(false);

        ToggleTurn();

    }

    private void ToggleTurn() {
        if(CheckWinner()){
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

            if(winner == 0){
                message.setText(R.string.tie);
                message.startAnimation(animation);
            }else if (winner==1){
                message.setText(player1.concat(getString(R.string.won)));
                message.startAnimation(animation);
            }else if (winner ==2){
                message.setText(player2.concat(getString(R.string.won)));
            }
            bt_Continue.setVisibility(View.VISIBLE);
            bt_Continue.startAnimation(animation);
            bt_Continue.setClickable(true);


            return;
        }
        if(turn == 1){
            turn = 2;
        }else{
            turn = 1;
        }

        if (turn == 1 ) {
            message.setText(player1.concat(getString(R.string.turn)));
        }else {
            message.setText(player2.concat(getString(R.string.turn)));
        }
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        message.startAnimation(animation);


        if (mode==1 && turn == 2){
            ComputerPlay();
        }else{
            unlockButtons();
        }
    }

    private void unlockButtons() {
        for(Button b: buttons){
            if(b.getText().toString().equals("")){
                b.setClickable(true);
            }
        }
    }

    private boolean CheckWinner() {

        MediaPlayer winnersound= MediaPlayer.create(MainActivity.this,R.raw.win_end);


        // check if player 1 won
        // row1
        if(buttons[0].getText().toString().equals("X") &&
                buttons[1].getText().toString().equals("X") &&
                buttons[2].getText().toString().equals("X")){
            winner =1;
            score1.setText(String.valueOf( 1 + Integer.parseInt(score1.getText().toString())));
            HighLight("row1");
            winnersound.start();
            return true;
        }
        // row 2
        if(buttons[3].getText().toString().equals("X") &&
                buttons[4].getText().toString().equals("X") &&
                buttons[5].getText().toString().equals("X")){
            winner =1;
            score1.setText(String.valueOf( 1 + Integer.parseInt(score1.getText().toString())));
            HighLight("row2");
            winnersound.start();
            return true;
        }
        // row 3
        if(buttons[6].getText().toString().equals("X") &&
                buttons[7].getText().toString().equals("X") &&
                buttons[8].getText().toString().equals("X")){
            winner =1;
            score1.setText(String.valueOf( 1 + Integer.parseInt(score1.getText().toString())));
            HighLight("row3");
            winnersound.start();
            return true;
        }
        // column 1
        if(buttons[0].getText().toString().equals("X") &&
                buttons[3].getText().toString().equals("X") &&
                buttons[6].getText().toString().equals("X")){
            winner =1;
            score1.setText(String.valueOf( 1 + Integer.parseInt(score1.getText().toString())));
            HighLight("column1");
            winnersound.start();
            return true;
        }
        //column 2
        if(buttons[1].getText().toString().equals("X") &&
                buttons[4].getText().toString().equals("X") &&
                buttons[7].getText().toString().equals("X")){
            winner =1;
            score1.setText(String.valueOf( 1 + Integer.parseInt(score1.getText().toString())));
            HighLight("column2");
            winnersound.start();
            return true;
        }
        //column 3
        if(buttons[2].getText().toString().equals("X") &&
                buttons[5].getText().toString().equals("X") &&
                buttons[8].getText().toString().equals("X")){
            winner =1;
            score1.setText(String.valueOf( 1 + Integer.parseInt(score1.getText().toString())));
            HighLight("column3");
            winnersound.start();
            return true;
        }
        // diagonal 1
        if(buttons[0].getText().toString().equals("X") &&
                buttons[4].getText().toString().equals("X") &&
                buttons[8].getText().toString().equals("X")){
            winner =1;
            score1.setText(String.valueOf( 1 + Integer.parseInt(score1.getText().toString())));
            HighLight("diagonal1");
            winnersound.start();
            return true;
        }
        //diagonal2
        if(buttons[2].getText().toString().equals("X") &&
                buttons[4].getText().toString().equals("X") &&
                buttons[6].getText().toString().equals("X")){
            winner =1;
            score1.setText(String.valueOf( 1 + Integer.parseInt(score1.getText().toString())));
            HighLight("diagonal2");
            winnersound.start();
            return true;
        }

        // check if player 2 won
        // row1
        if(buttons[0].getText().toString().equals("0") &&
                buttons[1].getText().toString().equals("0") &&
                buttons[2].getText().toString().equals("0")){
            winner =2;
            score2.setText(String.valueOf( 1 + Integer.parseInt(score2.getText().toString())));
            HighLight("row1");
            winnersound.start();
            return true;
        }
        // row 2
        if(buttons[3].getText().toString().equals("0") &&
                buttons[4].getText().toString().equals("0") &&
                buttons[5].getText().toString().equals("0")){
            winner =2;
            score2.setText(String.valueOf( 1 + Integer.parseInt(score2.getText().toString())));
            HighLight("row2");
            winnersound.start();
            return true;
        }
        // row 3
        if(buttons[6].getText().toString().equals("0") &&
                buttons[7].getText().toString().equals("0") &&
                buttons[8].getText().toString().equals("0")){
            winner =2;
            score2.setText(String.valueOf( 1 + Integer.parseInt(score2.getText().toString())));
            HighLight("row3");
            winnersound.start();
            return true;
        }
        // column 1
        if(buttons[0].getText().toString().equals("0") &&
                buttons[3].getText().toString().equals("0") &&
                buttons[6].getText().toString().equals("0")){
            winner =2;
            score2.setText(String.valueOf( 1 + Integer.parseInt(score2.getText().toString())));
            HighLight("column1");
            winnersound.start();
            return true;
        }
        //column 2
        if(buttons[1].getText().toString().equals("0") &&
                buttons[4].getText().toString().equals("0") &&
                buttons[7].getText().toString().equals("0")){
            winner =2;
            score2.setText(String.valueOf( 1 + Integer.parseInt(score2.getText().toString())));
            HighLight("column2");
            winnersound.start();
            return true;
        }
        //column 3
        if(buttons[2].getText().toString().equals("0") &&
                buttons[5].getText().toString().equals("0") &&
                buttons[8].getText().toString().equals("0")){
            winner =2;
            score2.setText(String.valueOf( 1 + Integer.parseInt(score2.getText().toString())));
            HighLight("column3");
            winnersound.start();
            return true;
        }
        // diagonal 1
        if(buttons[0].getText().toString().equals("0") &&
                buttons[4].getText().toString().equals("0") &&
                buttons[8].getText().toString().equals("0")){
            winner =2;
            score2.setText(String.valueOf( 1 + Integer.parseInt(score2.getText().toString())));
            HighLight("diagonal1");
            winnersound.start();
            return true;
        }
        if(buttons[2].getText().toString().equals("0") &&
                buttons[4].getText().toString().equals("0") &&
                buttons[6].getText().toString().equals("0")){
            winner =2;
            score2.setText(String.valueOf( 1 + Integer.parseInt(score2.getText().toString())));
            HighLight("diagonal2");
            winnersound.start();
            return true;
        }

        for(Button b : buttons){
            if (b.getText().toString().equals("")) {

                return false;
            }
        }


        winner = 0;
        winnersound.start();
        return true;
    }

    private void HighLight(String line) {

        switch (line){
            case("row1"):
                buttons[0].setBackgroundColor(Color.parseColor("#00000000"));
                buttons[1].setBackgroundColor(Color.parseColor("#00000000"));
                buttons[2].setBackgroundColor(Color.parseColor("#00000000"));
                break;
            case("row2"):
                buttons[3].setBackgroundColor(Color.parseColor("#00000000"));
                buttons[4].setBackgroundColor(Color.parseColor("#00000000"));
                buttons[5].setBackgroundColor(Color.parseColor("#00000000"));
                break;
            case("row3"):
                buttons[6].setBackgroundColor(Color.parseColor("#00000000"));
                buttons[7].setBackgroundColor(Color.parseColor("#00000000"));
                buttons[8].setBackgroundColor(Color.parseColor("#00000000"));
                break;
            case("column1"):
                buttons[0].setBackgroundColor(Color.parseColor("#00000000"));
                buttons[3].setBackgroundColor(Color.parseColor("#00000000"));
                buttons[6].setBackgroundColor(Color.parseColor("#00000000"));
                break;

            case("column2"):
                buttons[1].setBackgroundColor(Color.parseColor("#00000000"));
                buttons[4].setBackgroundColor(Color.parseColor("#00000000"));
                buttons[7].setBackgroundColor(Color.parseColor("#00000000"));
                break;
            case("column3"):
                buttons[2].setBackgroundColor(Color.parseColor("#00000000"));
                buttons[5].setBackgroundColor(Color.parseColor("#00000000"));
                buttons[8].setBackgroundColor(Color.parseColor("#00000000"));
                break;

            case("diagonal1"):
                buttons[0].setBackgroundColor(Color.parseColor("#00000000"));
                buttons[4].setBackgroundColor(Color.parseColor("#00000000"));
                buttons[8].setBackgroundColor(Color.parseColor("#00000000"));
                break;

            case("diagonal2"):
                buttons[2].setBackgroundColor(Color.parseColor("#00000000"));
                buttons[4].setBackgroundColor(Color.parseColor("#00000000"));
                buttons[6].setBackgroundColor(Color.parseColor("#00000000"));
                break;

        }
    }

    private void ComputerPlay() {

        lockButtons();

        Random r = new Random();

        List<Integer> possiblesIndex =  new ArrayList<>();


        Handler delay = new Handler();
        delay.postDelayed(() -> {
            for(int i =0; i<9; i++){
                if(buttons[i].getText().equals("")){
                    possiblesIndex.add(i);
                }
            }

            int index = r.nextInt(possiblesIndex.size());
            buttons[possiblesIndex.get(index)].setText("0");
            MediaPlayer pencil= MediaPlayer.create(MainActivity.this,R.raw.pencil);
            pencil.start();

            unlockButtons();
            ToggleTurn();

        }, 3000);

    }

    private void lockButtons() {
        for(Button b: buttons){
            b.setClickable(false);
        }
    }

    private void MessageDelayed(String s) {

        Handler delay = new Handler();
        delay.postDelayed(() -> {
            message.setText(s);


            for(Button b: buttons){
                b.setClickable(b.getText().equals(""));
            }
        }, 3000);

    }

    public void Continue (View view){
        for(Button b: buttons){
            b.setText("");
        }
        winner = null;
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        bt_Continue.startAnimation(animation);
        bt_Continue.setClickable(false);
        MediaPlayer continues = MediaPlayer.create(MainActivity.this,R.raw.continue_);
        continues.start();




        StartGame();
    }


    public void ChooseNames(View view){
        if(tv_player1Name.getText().toString().equals("")){
            player1 = "Player 1";
        }else{
            player1 = tv_player1Name.getText().toString();
        }

        if(tv_player2Name.getText() == null || tv_player2Name.getText().toString().equals("")){
            player2 = "Player 2";
        }else{
            player2 = tv_player2Name.getText().toString();
        }

        goGame(view);
    }

}