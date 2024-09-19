package com.shadow.gamebyshadowamitendu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class player_names extends AppCompatActivity {

    private EditText player1;
    private EditText player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_names);
        player1 = findViewById(R.id.player1EditTxt);
        player2 = findViewById(R.id.player2EditTxt);
    }

    public void startGameBtn(View view) {
        String player1Name = player1.getText().toString();
        String player2Name = player2.getText().toString();

        Intent intent = new Intent(this, Gamedisplay.class);
        intent.putExtra("Player_Names", new String[] {player1Name, player2Name});
        startActivity(intent);
    }
}