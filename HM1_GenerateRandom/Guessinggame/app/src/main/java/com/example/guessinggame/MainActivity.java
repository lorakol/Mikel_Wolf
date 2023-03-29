package com.example.guessinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;
/*
    The below described app will be a four number guessing game.
    The application will generate four random numbers between 0 and 9 (inclusively) and
    the user will have four attempts at guessing the right combination of numbers.
    After each guess, the app will let the user know if they were correct or if each number was too high or too low.
*/

public class MainActivity extends AppCompatActivity {

    Button btnSubmit;
    EditText[] editGuessArray;
    int[] randomArray;
    Random randObj;
    final int GUESS_SIZE = 4;
    int remainGuessCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        randomArray = new int[GUESS_SIZE];
        editGuessArray = new EditText[GUESS_SIZE];
        randObj = new Random();

        btnSubmit = (Button) findViewById(R.id.button_addition);
        editGuessArray[0] = (EditText) findViewById(R.id.edit_guess1);
        editGuessArray[1] = (EditText) findViewById(R.id.edit_guess2);
        editGuessArray[2] = (EditText) findViewById(R.id.edit_guess3);
        editGuessArray[3] = (EditText) findViewById(R.id.edit_guess4);
        startGame();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSubmit();
            }
        });

    }

    private void clickSubmit(){
        for(int i = 0 ; i < GUESS_SIZE ; i++){
            if(editGuessArray[i].getText().toString().equals("")){
                Toast.makeText(this, "Please enter number values in ALL fields", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        boolean bSuccess = true;
        for(int i = 0 ; i < GUESS_SIZE ; i++){
            editGuessArray[i].setTextColor(getColor(editGuessArray[i].getText().toString(), randomArray[i]));
            if(editGuessArray[i].getCurrentTextColor() != Color.BLUE)
                bSuccess = false;
        }
        remainGuessCount--;
        if(bSuccess || remainGuessCount == 0){
            showAlert(bSuccess);
        }
        else{
            Toast.makeText(this, remainGuessCount + " guess left", Toast.LENGTH_SHORT).show();
        }


    }

    private int getColor(String guess, int value){
        int nGuess = Integer.parseInt(guess);
        if(nGuess < value)
            return Color.BLUE;
        if(nGuess > value)
            return Color.RED;
        return Color.GREEN;

    }

    private void startGame(){
        for(int i = 0 ; i < GUESS_SIZE ; i ++){
            randomArray[i] = randObj.nextInt(10);
            editGuessArray[i].setText("");
            editGuessArray[i].setTextColor(Color.BLACK);
        }
        remainGuessCount = GUESS_SIZE;
    }

    private void showAlert(boolean bSuccess){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Play again?");
        if(bSuccess)
            builder.setTitle("You Win!");
        else
            builder.setTitle("You Lost.");
        builder.setCancelable(false);
        builder.setPositiveButton("Restart", (DialogInterface.OnClickListener) (dialog, which) -> {
            startGame();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}