package com.training.digginggame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.training.digginggame.activity.GameActivity;
import com.training.digginggame.object.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoardView extends SurfaceView implements Runnable  {
    private Bitmap groundTexture;
    private Bitmap holeTexture;
    //private List<Item> items = new ArrayList<>();
    private Paint paint = new Paint();
    //private static int GAME_BOARD_SIZE = 400;
    private static int DIG_RADIUS = 20;
    Thread renderThread = null;
    SurfaceHolder holder;
    volatile boolean running = false;
    boolean isTouch = true;
    ArrayList<Integer> touchxlist = new ArrayList<>();
    ArrayList<Integer> touchylist = new ArrayList<>();

    GameModel gameModel = new GameModel();
    public GameBoardView(Context context, GameModel model) {
        super(context);
        holder = getHolder();
        this.gameModel = model;
        // Initialization...
        initializeItems(context);
        init();
    }

    private void init() {
        groundTexture = BitmapFactory.decodeResource(getResources(), R.drawable.field);
        holeTexture = BitmapFactory.decodeResource(getResources(), R.drawable.hole);
        paint.setAntiAlias(true);
    }



    private void drawGameBoard() {
        //Canvas canvas = null;
        try {
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                // Fill the canvas with the ground texture
//                if(!isDrawBack){
//                    canvas.drawBitmap(groundTexture, 0, 0, paint);
//                    isDrawBack = true;
//                }
                canvas.drawBitmap(groundTexture, 0, 0, paint);
                // Draw items and holes (this is a simplistic approach)
                for (Item item : gameModel.getItems()) {
                    if (item.isFound) {
                        canvas.drawBitmap(item.texture, item.x, item.y, paint);
                    } else {
                        //canvas.drawBitmap(holeTexture, item.x, item.y, paint);
                    }
                }
                for(int i = 0 ; i < touchxlist.size(); i++){
                    canvas.drawBitmap(holeTexture, touchxlist.get(i), touchylist.get(i), paint);
                }

                holder.unlockCanvasAndPost(canvas);
            }
        } finally {
//            if (canvas != null) {
//                holder.unlockCanvasAndPost(canvas);
//            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            handleTap(event.getX(), event.getY());
            isTouch = true;
        }
        return true;
    }

    private void handleTap(float x, float y) {
        // Check for items within a certain radius of the tap
        boolean bFound = false;
        for (Item item : gameModel.getItems()) {
            if(item.isFound)
                continue;
            if (Math.sqrt(Math.pow(item.x - x, 2) + Math.pow(item.y - y, 2)) < DIG_RADIUS) {
                item.isFound = true;
                bFound = true;
                gameModel.addFoundItem(item);
                // Show a toast with the found item
                Toast.makeText(getContext(), "Found: " + item.name, Toast.LENGTH_SHORT).show();
            }
        }
        if(!bFound){
            touchxlist.add((int)x);
            touchylist.add((int)y);
        }
        //drawGameBoard();  // Refresh the board to reflect the changes
    }

    private List<Item> parseCSV(Context context) {
        List<Item> items = new ArrayList<>();
        InputStream inputStream = context.getResources().openRawResource(R.raw.items);
        // Assuming your CSV file is named treasure_data.csv and placed in the res/raw directory.

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        try {
            int idx = 1;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {  // Basic check to ensure we have both name and value
                    Item item = new Item();
                    item.id = idx;
                    idx++;
                    item.name = parts[0].trim();
                    item.value = Integer.parseInt(parts[1].trim());
                    // Assuming each line in your CSV has the format: "itemName,value"
                    items.add(item);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return items;
    }

    private void initializeItems(Context context) {
        List<Item> parsedItems = parseCSV(context);

        for (Item item : parsedItems) {
            item.texture = BitmapFactory.decodeResource(getResources(), R.drawable.nice);
            // Assign a random position to each item on the game board.
            item.x = randomXPosition();  // Create a method to generate random positions
            item.y = randomYPosition();
            gameModel.addItem(item);
        }

        //items.addAll(parsedItems);  // Assuming 'items' is the global list of items in the game board.
    }

    private int randomXPosition() {
        // Returns a random position within the bounds of the game board.
        return new Random().nextInt(GameActivity.SCREEN_WIDTH - DIG_RADIUS);  // Assuming GAME_BOARD_SIZE is the maximum coordinate value.
    }

    private int randomYPosition() {
        // Returns a random position within the bounds of the game board.
        return new Random().nextInt(GameActivity.SCREEN_HEIGHT - DIG_RADIUS);  // Assuming GAME_BOARD_SIZE is the maximum coordinate value.
    }

    public void resume() {
        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }

    public void goToInventory(){
        isTouch = true;
        //runOnUiThread(callInventory);
    }

    @Override
    public void run() {
        while (running) {
            if (!holder.getSurface().isValid())
                continue;
            if(isTouch){
                drawGameBoard();
                isTouch = false;
            }

//            Canvas canvas = holder.lockCanvas();
//            canvas.drawBitmap(groundTexture, 0, 0, paint);
//            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        running = false;
        while (true) {
            try {
                renderThread.join();
            } catch (InterruptedException e) {
                // retry
            }
        }
    }
}
