package com.training.digginggame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.training.digginggame.GameBoardView;
import com.training.digginggame.GameModel;
import com.training.digginggame.R;

public class GameActivity extends AppCompatActivity {

    public static GameModel gameModel;
    private GameBoardView gameBoardView;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    Runnable callInventory = new Runnable() {
        public void run() {
            Intent intent=new Intent(GameActivity.this,InventoryActivity.class);

            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        SCREEN_HEIGHT = displayMetrics.heightPixels;
        SCREEN_WIDTH = displayMetrics.widthPixels;

        gameModel = new GameModel();
        gameBoardView = new GameBoardView(this, gameModel);
        setContentView(gameBoardView);

        // Other initialization...
    }

    protected void onResume() {
        super.onResume();
        gameBoardView.resume();
    }
    protected void onPause() {
        super.onPause();
        //gameBoardView.pause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.inventoryMenu) {
            // Start InventoryActivity here
            //gameBoardView.pause();
            gameBoardView.goToInventory();
            Intent switchActivityIntent = new Intent(this, InventoryActivity.class);
            startActivity(switchActivityIntent);

            //runOnUiThread(callInventory);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
