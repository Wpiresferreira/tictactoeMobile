package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    int mode; // it stores if 1-player or 2-player mode
    int turn;  // it stores who plays next time
    int scorePlayer1, scorePlayer2 = 0; // store the scores.
    Integer winner = null; // null=game running, 0=tie, 1=player1 won, 2=player2 won.
    TextView score1, score2, tv_player1, tv_player2, message; // they will be used to change screen content;
    String player1, player2; // store the player's name
    TextView tv_player1Name, tv_player2Name ;
Button bt_Continue;
    Button[] buttons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            tv_player2Name.setText("Computer");
            tv_player2Name.setVisibility(View.INVISIBLE);
        }

    }
    private void StartGame() {
        for (Button b:buttons){
            b.setClickable(false);
            b.setBackgroundColor(Color.parseColor("#0F0F50"));
        }

        winner = null;

        Random randomPlayer = new Random();
        turn = randomPlayer.nextInt(2) + 1;
        message.setText(R.string.sorting);

        String firstMessage;
        if(turn == 1){
            firstMessage = player1 + " start";
        }else{
            firstMessage = player2 + " start";
        }

        MessageDelayed(firstMessage, 3000);

        Handler delay = new Handler();
        delay.postDelayed(new Runnable() {
            @Override
            public void run() {
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
        buttonClicked.setClickable(false);

        ToggleTurn();

    }

    private void ToggleTurn() {
        if(CheckWinner()){

            if(winner == 0){
                message.setText("Tie");
            }else if (winner==1){
                message.setText(player1 + " won!");
            }else if (winner ==2){
                message.setText(player2 + " won!");
            }
            bt_Continue.setVisibility(View.VISIBLE);
            return;
        }
        if(turn == 1){
            turn = 2;
        }else{
            turn = 1;
        }

        if (turn == 1 ) {
            message.setText(player1 + " turn");
        }else {
            message.setText(player2 + " turn");
        }

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


        // check if player 1 won
        // row1
        if(buttons[0].getText().toString().equals("X") &&
                buttons[1].getText().toString().equals("X") &&
                buttons[2].getText().toString().equals("X")){
            winner =1;
            score1.setText(String.valueOf( 1 + Integer.parseInt(score1.getText().toString())));
            HighLight("row1");
            return true;
        }
        // row 2
        if(buttons[3].getText().toString().equals("X") &&
                buttons[4].getText().toString().equals("X") &&
                buttons[5].getText().toString().equals("X")){
            winner =1;
            score1.setText(String.valueOf( 1 + Integer.parseInt(score1.getText().toString())));
            HighLight("row2");
            return true;
        }
        // row 3
        if(buttons[6].getText().toString().equals("X") &&
                buttons[7].getText().toString().equals("X") &&
                buttons[8].getText().toString().equals("X")){
            winner =1;
            score1.setText(String.valueOf( 1 + Integer.parseInt(score1.getText().toString())));
            HighLight("row3");
            return true;
        }
        // column 1
        if(buttons[0].getText().toString().equals("X") &&
                buttons[3].getText().toString().equals("X") &&
                buttons[6].getText().toString().equals("X")){
            winner =1;
            score1.setText(String.valueOf( 1 + Integer.parseInt(score1.getText().toString())));
            HighLight("column1");
            return true;
        }
        //column 2
        if(buttons[1].getText().toString().equals("X") &&
                buttons[4].getText().toString().equals("X") &&
                buttons[7].getText().toString().equals("X")){
            winner =1;
            score1.setText(String.valueOf( 1 + Integer.parseInt(score1.getText().toString())));
            HighLight("column2");
            return true;
        }
        //column 3
        if(buttons[2].getText().toString().equals("X") &&
                buttons[5].getText().toString().equals("X") &&
                buttons[8].getText().toString().equals("X")){
            winner =1;
            score1.setText(String.valueOf( 1 + Integer.parseInt(score1.getText().toString())));
            HighLight("column3");
            return true;
        }
        // diagonal 1
        if(buttons[0].getText().toString().equals("X") &&
                buttons[4].getText().toString().equals("X") &&
                buttons[8].getText().toString().equals("X")){
            winner =1;
            score1.setText(String.valueOf( 1 + Integer.parseInt(score1.getText().toString())));
            HighLight("diagonal1");
            return true;
        }
        //diagonal2
        if(buttons[2].getText().toString().equals("X") &&
                buttons[4].getText().toString().equals("X") &&
                buttons[6].getText().toString().equals("X")){
            winner =1;
            score1.setText(String.valueOf( 1 + Integer.parseInt(score1.getText().toString())));
            HighLight("diagonal2");
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
            return true;
        }
        // row 2
        if(buttons[3].getText().toString().equals("0") &&
                buttons[4].getText().toString().equals("0") &&
                buttons[5].getText().toString().equals("0")){
            winner =2;
            score2.setText(String.valueOf( 1 + Integer.parseInt(score2.getText().toString())));
            HighLight("row2");
            return true;
        }
        // row 3
        if(buttons[6].getText().toString().equals("0") &&
                buttons[7].getText().toString().equals("0") &&
                buttons[8].getText().toString().equals("0")){
            winner =2;
            score2.setText(String.valueOf( 1 + Integer.parseInt(score2.getText().toString())));
            HighLight("row3");
            return true;
        }
        // column 1
        if(buttons[0].getText().toString().equals("0") &&
                buttons[3].getText().toString().equals("0") &&
                buttons[6].getText().toString().equals("0")){
            winner =2;
            score2.setText(String.valueOf( 1 + Integer.parseInt(score2.getText().toString())));
            HighLight("column1");
            return true;
        }
        //column 2
        if(buttons[1].getText().toString().equals("0") &&
                buttons[4].getText().toString().equals("0") &&
                buttons[7].getText().toString().equals("0")){
            winner =2;
            score2.setText(String.valueOf( 1 + Integer.parseInt(score2.getText().toString())));
            HighLight("column2");
            return true;
        }
        //column 3
        if(buttons[2].getText().toString().equals("0") &&
                buttons[5].getText().toString().equals("0") &&
                buttons[8].getText().toString().equals("0")){
            winner =2;
            score2.setText(String.valueOf( 1 + Integer.parseInt(score2.getText().toString())));
            HighLight("column3");
            return true;
        }
        // diagonal 1
        if(buttons[0].getText().toString().equals("0") &&
                buttons[4].getText().toString().equals("0") &&
                buttons[8].getText().toString().equals("0")){
            winner =2;
            score2.setText(String.valueOf( 1 + Integer.parseInt(score2.getText().toString())));
            HighLight("diagonal1");
            return true;
        }
        if(buttons[2].getText().toString().equals("0") &&
                buttons[4].getText().toString().equals("0") &&
                buttons[6].getText().toString().equals("0")){
            winner =2;
            score2.setText(String.valueOf( 1 + Integer.parseInt(score2.getText().toString())));
            HighLight("diagonal2");
            return true;
        }

        for(Button b : buttons){
            if (b.getText().toString().equals("")) {

                return false;
            }
        }


        winner = 0;
        return true;
    }

    private void HighLight(String line) {

        switch (line){
            case("row1"):
                buttons[0].setBackgroundColor(Color.GRAY);
                buttons[1].setBackgroundColor(Color.GRAY);
                buttons[2].setBackgroundColor(Color.GRAY);
                break;
            case("row2"):
                buttons[3].setBackgroundColor(Color.GRAY);
                buttons[4].setBackgroundColor(Color.GRAY);
                buttons[5].setBackgroundColor(Color.GRAY);
                break;
            case("row3"):
                buttons[6].setBackgroundColor(Color.GRAY);
                buttons[7].setBackgroundColor(Color.GRAY);
                buttons[8].setBackgroundColor(Color.GRAY);
                break;
            case("column1"):
                buttons[0].setBackgroundColor(Color.GRAY);
                buttons[3].setBackgroundColor(Color.GRAY);
                buttons[6].setBackgroundColor(Color.GRAY);
                break;

            case("column2"):
                buttons[1].setBackgroundColor(Color.GRAY);
                buttons[4].setBackgroundColor(Color.GRAY);
                buttons[7].setBackgroundColor(Color.GRAY);
                break;
            case("column3"):
                buttons[2].setBackgroundColor(Color.GRAY);
                buttons[5].setBackgroundColor(Color.GRAY);
                buttons[8].setBackgroundColor(Color.GRAY);
                break;

            case("diagonal1"):
                buttons[0].setBackgroundColor(Color.GRAY);
                buttons[4].setBackgroundColor(Color.GRAY);
                buttons[8].setBackgroundColor(Color.GRAY);
                break;

            case("diagonal2"):
                buttons[2].setBackgroundColor(Color.GRAY);
                buttons[4].setBackgroundColor(Color.GRAY);
                buttons[6].setBackgroundColor(Color.GRAY);
                break;

        }
    }

    private void ComputerPlay() {

        lockButtons();

        Random r = new Random();

        List<Integer> possiblesIndex =  new ArrayList<>();


        Handler delay = new Handler();
        delay.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i =0; i<9; i++){
                    if(buttons[i].getText().equals("")){
                        possiblesIndex.add(i);
                    }
                }

                int index = r.nextInt(possiblesIndex.size());
                buttons[possiblesIndex.get(index)].setText("0");
                unlockButtons();
                ToggleTurn();

            }
        }, 3000);

    }

    private void lockButtons() {
        for(Button b: buttons){
            b.setClickable(false);
        }
    }

    private void MessageDelayed(String s, int time) {

        Handler delay = new Handler();
        delay.postDelayed(new Runnable() {
            @Override
            public void run() {
                message.setText(s);

                for(Button b: buttons){
                    if(b.getText().equals("")){
                        b.setClickable(true);
                    }else{
                        b.setClickable(false);
                    }
                }
            }
        }, time);

    }

    public void Continue (View view){
        for(Button b: buttons){
            b.setText("");
        }
        winner = null;
        bt_Continue.setVisibility(View.INVISIBLE);
        StartGame();
    }


    public void ChooseNames(View view){
        if(tv_player1Name.getText().toString().equals("")){
            player1 = "Player1";
        }else{
            player1 = tv_player1Name.getText().toString();
        }

        if(tv_player2Name.getText() == null || tv_player2Name.getText().toString().equals("")){
            player2 = "Player2";
        }else{
            player2 = tv_player2Name.getText().toString();
        }

        goGame(view);
    }
}


