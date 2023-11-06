package com.training.myphone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.data.FreezableUtils;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, DataApi.DataListener, MessageApi.MessageListener, GoogleApiClient.OnConnectionFailedListener {

    public static MainActivity mContext;

    private GoogleApiClient mGoogleApiClient;
    ListView audioView;
    private List<String> selectedDummyClips = new ArrayList<>();
    static ArrayList<String> listData = new ArrayList<String>();
    static ArrayAdapter<String> mArrayAdapter;
    static ArrayAdapter<String> dummyClipsAdapter;
    private MediaPlayer mediaPlayer;
    private int startTrimPosition = 0;
    private int endTrimPosition = 0;
    Button btnTrimStart;
    Button btnTrimEnd;
    private Button btnEdit;
    ListView listDummyClips;
    private Handler mHandler;
    private LinearLayout audioClipContainer;
    private LinearLayout dummyClipsLayout;
    private TextView trash;

    public static ArrayList<String> dummyClipTitles  = new ArrayList<>();
//    {
//            "old_radio_noise",
//            "radio_transmission_morse_code",
//            "number_station"
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dummyClipTitles.add("old_radio_noise");
        dummyClipTitles.add("radio_transmission_morse_code");
        dummyClipTitles.add("number_station");
        mHandler = new Handler();
        setContentView(R.layout.activity_main);
         btnTrimStart = findViewById(R.id.btnTrimStart);
         btnTrimEnd = findViewById(R.id.btnTrimEnd);
        audioView = findViewById(R.id.listaudio);
       listDummyClips = findViewById(R.id.listDummyClips);
        audioClipContainer = findViewById(R.id.audioClipContainer);
        dummyClipsLayout = findViewById(R.id.dummyClipsLayout);
        trash = findViewById(R.id.trash);
        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setVisibility(View.GONE);

        // Generate dummy audio clips
        generateDummyAudioClips();

        // Set drag listeners
        audioClipContainer.setOnDragListener(new AudioDragListener());
        trash.setOnDragListener(new TrashDragListener());

        mArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listData);
        audioView.setAdapter(mArrayAdapter);
        audioView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                audioPlayer(selectedItem + ".mp3");
                Toast.makeText(MainActivity.this, "Play audio", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up the ArrayAdapter to populate the dummy clips data into the ListView
        dummyClipsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, dummyClipTitles);
        listDummyClips.setAdapter(dummyClipsAdapter);

        listDummyClips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedClipTitle = dummyClipTitles.get(i);
                // Open the EditAudioActivity with the selected dummy clip
                openEditAudioActivity(selectedClipTitle);
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {

                        Intent intent = new Intent(MainActivity.this, DataListenerService.class);
                        startService(intent);

                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .build();
        mGoogleApiClient.connect();
        mContext = this;
        btnTrimStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTrimPosition = mediaPlayer.getCurrentPosition();
                Toast.makeText(MainActivity.this, "Start Trim: " + startTrimPosition, Toast.LENGTH_SHORT).show();
            }
        });

        btnTrimEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTrimPosition = mediaPlayer.getCurrentPosition();
                btnTrimStart.setVisibility(View.GONE);
                btnEdit.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "End Trim: " + endTrimPosition, Toast.LENGTH_SHORT).show();

            }
        });

        listDummyClips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedClipTitle = dummyClipTitles.get(i);

                // Toggle selection of the dummy clip
                if (selectedDummyClips.contains(selectedClipTitle)) {
                    selectedDummyClips.remove(selectedClipTitle);
                    view.setBackgroundColor(Color.LTGRAY); // Unselect the clip
                } else {
                    audioPlayer(selectedClipTitle);
                    selectedDummyClips.add(selectedClipTitle);
                    view.setBackgroundColor(Color.CYAN); // Select the clip
                }
            }
        });
    }



    public static void addDummyClipTitle(String editedDummyClipTitle) {
        // Add the edited dummy clip title to the list
        //listData.add(editedDummyClipTitle);
        //dummyClipsAdapter.add(editedDummyClipTitle);
        //dummyClipsAdapter.notifyDataSetChanged();
        dummyClipTitles.add(editedDummyClipTitle);
        // Notify the ArrayAdapter that the data has changed
        //mArrayAdapter.notifyDataSetChanged();
    }


    public void onEditButtonClick(View view) {
        // Check if at least one dummy clip is selected
        if (selectedDummyClips.isEmpty()) {
            Toast.makeText(this, "Please select at least one dummy clip.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Proceed to EditAudioActivity with the selected dummy clip titles
        Intent intent = new Intent(this, EditAudioActivity.class);
        intent.putStringArrayListExtra("selectedDummyClips", new ArrayList<>(selectedDummyClips));
        startActivity(intent);
    }


    public void onTrimStartButtonClick(View view) {
        startTrimPosition = mediaPlayer.getCurrentPosition();
        Toast.makeText(MainActivity.this, "Start Trim: " + startTrimPosition, Toast.LENGTH_SHORT).show();
    }



    private void generateDummyAudioClips() {
        // Dummy audio clips
        String[] dummyClips = {"Clip 1", "Clip 2", "Clip 3"};

        for (String clip : dummyClips) {
            TextView textView = new TextView(this);
            textView.setText(clip);
            textView.setBackgroundColor(Color.LTGRAY);
            textView.setTextColor(Color.BLACK);
            textView.setPadding(16, 8, 16, 8);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 8, 0, 0);
            textView.setLayoutParams(params);
            textView.setOnLongClickListener(new ClipLongClickListener());
            dummyClipsLayout.addView(textView);
        }
    }
    private void openEditAudioActivity(String selectedClipTitle) {
        Intent intent = new Intent(this, EditAudioActivity.class);
        intent.putExtra("selectedClipTitle", selectedClipTitle);

        // Add an extra to identify that it's an edited clip
        if (selectedClipTitle.equals("Edited Clip")) {
            intent.putExtra("isEditedClip", true);
        }

        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
        updateButtonVisibility();
        //dummyClipsAdapter.clear();

        //dummyClipsAdapter.addAll(dummyClipTitles);
        dummyClipsAdapter.notifyDataSetChanged();

    }
    private void updateButtonVisibility() {
        if (selectedDummyClips.isEmpty()) {
            // No dummy clips are selected, show the trim button
            btnTrimStart.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.GONE);
        } else {
            // At least one dummy clip is selected
            if (selectedDummyClips.contains("Edited Clip")) {
                // Edited clip is selected, hide the edit button
                btnTrimStart.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.GONE);
            } else {
                // Edited clip is not selected, show the edit button
                btnTrimStart.setVisibility(View.GONE);
                btnEdit.setVisibility(View.VISIBLE);
            }
        }
    }



    public void audioPlayer(String path){

//        String mp3File = "raw/"+path+".mp3";
//        AssetManager assetMan = getAssets();
//        mediaPlayer = new MediaPlayer();
//        FileInputStream mp3Stream = null;
        try {
            int resID = getResources().getIdentifier(path, "raw", getPackageName());
            //mp3Stream = assetMan.openFd(mp3File).createInputStream();
            //mediaPlayer.setDataSource(mp3Stream.getFD());
            mediaPlayer=MediaPlayer.create(this,resID);//R.raw.old_radio_noise
            //mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //set up MediaPlayer



        startTrimPosition = 0;
        endTrimPosition = 0;
    }
    private void trimAudio(String inputPath, String outputPath) {
        String trimmedAudioPath = AudioTrimmer.trimAudio(inputPath, outputPath, startTrimPosition * 1000, endTrimPosition * 1000);

        if (trimmedAudioPath != null) {
            // The audio has been successfully trimmed. You can proceed with other tasks here.
        } else {
            // Trimming failed. Handle the error accordingly.
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
        Wearable.DataApi.removeListener(mGoogleApiClient, this);
        Wearable.MessageApi.removeListener(mGoogleApiClient, this);
        //Wearable.NodeApi.re removeListener(mGoogleApiClient, this);
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Wearable.DataApi.addListener(mGoogleApiClient, this);
        Wearable.MessageApi.addListener(mGoogleApiClient, this);
        //Wearable.NodeApi.add addListener(mGoogleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onDataChanged(@NonNull DataEventBuffer dataEvents) {
        final List<DataEvent> events = FreezableUtils.freezeIterable(dataEvents);
        dataEvents.close();
        for (DataEvent event : events) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                String path = event.getDataItem().getUri().getPath();
                if ("/audio_data".equals(path)) {
                    DataMapItem dataMapItem = DataMapItem.fromDataItem(event.getDataItem());
                    Asset photo = dataMapItem.getDataMap().getAsset("audio_data");
                    final String audio_path = loadAudio(photo);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            addAudio(audio_path);
                        }
                    });
                }
            }
        }
    }

    private void addAudio(final String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mArrayAdapter.add(title);
                mArrayAdapter.notifyDataSetChanged();
            }
        });
    }


    private String loadAudio(Asset asset_audio){
        InputStream assetInputStream = Wearable.DataApi.getFdForAsset(
                mGoogleApiClient, asset_audio).await().getInputStream();
        DataInputStream in = new DataInputStream(assetInputStream);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        try (FileOutputStream outputStream = new FileOutputStream(new File(timeStamp + ".mp3"), false)) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }

    @Override
    public void onMessageReceived(@NonNull MessageEvent messageEvent) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }







    public static class ClipLongClickListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View view) {
            // Drag the clip to the target
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDragAndDrop(null, shadowBuilder, view, 0);
            return true;
        }
    }

    private class AudioDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View view, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DROP:
                    View draggedView = (View) event.getLocalState();
                    int draggedViewId = draggedView.getId();
                    int containerId = audioClipContainer.getId();
                    int trashId = trash.getId();

                    if (draggedViewId == containerId) {
                        // Dragged view is coming from the dummy clips container
                        dummyClipsLayout.removeView(draggedView);
                        audioClipContainer.addView(draggedView);
                        draggedView.setOnLongClickListener(null);
                        draggedView.setOnTouchListener(new ClipTouchListener());
                    }
                    break;
            }
            return true;
        }
    }

    private class TrashDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View view, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DROP:
                    View draggedView = (View) event.getLocalState();
                    int draggedViewId = draggedView.getId();
                    int containerId = audioClipContainer.getId();
                    int trashId = trash.getId();

                    if (draggedViewId == containerId) {
                        // Dragged view is coming from the audio clip container
                        audioClipContainer.removeView(draggedView);
                        dummyClipsLayout.addView(draggedView);
                        draggedView.setOnLongClickListener(new ClipLongClickListener());
                        draggedView.setOnTouchListener(null);
                    } else if (draggedViewId == trashId) {
                        // Dragged view is coming from the trash bin
                        dummyClipsLayout.removeView(draggedView);
                    }
                    break;
            }
            return true;
        }
    }

}