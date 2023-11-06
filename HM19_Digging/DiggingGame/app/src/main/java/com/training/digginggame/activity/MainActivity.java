package com.training.digginggame.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.training.digginggame.GameBoardView;
import com.training.digginggame.R;

public class MainActivity extends AppCompatActivity {

    boolean isStartGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isStartGame = false;
        Button startButton = findViewById(R.id.startGameButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });

        Button creditButton = findViewById(R.id.creditsButton);
        creditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCredit();
            }
        });
    }

    private void startGame(){
        isStartGame = true;
        Intent switchActivityIntent = new Intent(this, GameActivity.class);
        startActivity(switchActivityIntent);
    }

    private void goToCredit(){
        Intent switchActivityIntent = new Intent(this, CreditActivity.class);
        startActivity(switchActivityIntent);
    }

    protected void onResume() {
        super.onResume();
    }
    protected void onPause() {
        super.onPause();
    }
}