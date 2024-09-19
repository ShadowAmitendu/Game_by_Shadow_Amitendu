package com.shadow.gamebyshadowamitendu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Gamedisplay extends AppCompatActivity {

    private TicTacToeBoard ticTacToeBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_display);
        ticTacToeBoard = findViewById(R.id.ticTacToeBoard);

        Button playAgainBTN = findViewById(R.id.play_Again);
        Button homeBTN = findViewById(R.id.home_btn);
        TextView playersTurn = findViewById(R.id.playerDisplay);

        playAgainBTN.setVisibility(View.GONE);
        homeBTN.setVisibility(View.GONE);

        String[] playerNames = getIntent().getStringArrayExtra("Player_Names");
        if (playerNames != null){
            playersTurn.setText((playerNames[0] + "'s Turn"));
        }
        ticTacToeBoard.setUpGame(playAgainBTN, homeBTN, playersTurn, playerNames);
    }

    public void playAgainBtn(View view) {
        ticTacToeBoard.resetGame();
        ticTacToeBoard.invalidate();
    }

    public void homeBtnclick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}