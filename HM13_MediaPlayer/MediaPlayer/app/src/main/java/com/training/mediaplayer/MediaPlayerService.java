package com.training.mediaplayer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.Random;

public class MediaPlayerService extends Service implements MediaPlayer.OnCompletionListener {

    private final IBinder binder = new MediaPlayerBinder();
    private MediaPlayer mediaPlayer = null;
    private boolean isLooping = false;
    private boolean isShuffling = false;
    ArrayList<Integer> audioList = new ArrayList<>();
    ArrayList<String> titleList = new ArrayList<>();
    int currentIndex = 0;
    @Override
    public void onCreate() {
        super.onCreate();
        audioList.add(R.raw.i_see_you);
        audioList.add(R.raw.lean_on_me);
        audioList.add(R.raw.right_love);
        titleList.add("I see you");
        titleList.add("Lean on me");
        titleList.add("Right Love");
        createNotificationChannel();

        initializePlayer(audioList.get(currentIndex));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        if (action == null)
            return flags;


        switch (action) {
            case NotificationReceiver.ACTION_PLAY:
                play();
                break;
            case NotificationReceiver.ACTION_PAUSE:
                pause();
                break;
            case NotificationReceiver.ACTION_NEXT:
                skipNext();
                break;
            case NotificationReceiver.ACTION_PREV:
                skipPrevious();
                break;
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.release();
        if(isShuffling) {
            currentIndex = new Random().nextInt(audioList.size());
        }
        else{
            if(!isLooping){
                currentIndex++;
            }
        }
        if(currentIndex < audioList.size()){
            initializePlayer(audioList.get(currentIndex));
            mediaPlayer.setOnCompletionListener(this);
            play();
        }

    }

    public class MediaPlayerBinder extends Binder {
        public MediaPlayerService getService() {
            return MediaPlayerService.this;
        }
    }

    public void initializePlayer(int resourceId) {
        mediaPlayer = MediaPlayer.create(this, resourceId);
        mediaPlayer.setOnCompletionListener(this);
    }

    public void setLooping(boolean bLoop){
        mediaPlayer.setLooping(bLoop);
        isLooping = bLoop;
    }

    public void setShuffling(boolean bShuff){
        isShuffling = bShuff;
    }

    public void play() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            // Assuming placeholder song title and album art bitmap for now
        }
        else{
            initializePlayer(audioList.get(currentIndex));
        }
        showNotification(titleList.get(currentIndex), null);
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            removeNotification();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            removeNotification();
        }
    }

    public void skipNext() {
        // Placeholder for skipping to the next song logic
        currentIndex++;
        if (currentIndex >= audioList.size()) {
            currentIndex = 0;
        }
        mediaPlayer.release();
        initializePlayer(audioList.get(currentIndex));
        mediaPlayer.setOnCompletionListener(this);
        play();
    }

    public void skipPrevious() {
        // Placeholder for skipping to the previous song logic
        currentIndex--;
        if (currentIndex < 0 ) {
            currentIndex = audioList.size() - 1;
        }
        mediaPlayer.release();
        initializePlayer(audioList.get(currentIndex));
        mediaPlayer.setOnCompletionListener(this);
        play();
    }

    public void seekTo(int position) {
        if (mediaPlayer != null) {
            int currentPosition = position * mediaPlayer.getDuration() / 100;
            mediaPlayer.seekTo(currentPosition);
        }
    }

    public int getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public String getCurrentTitle(){
        return titleList.get(currentIndex);
    }

    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "MEDIA_PLAYBACK_CHANNEL",
                    "Media Playback",
                    NotificationManager.IMPORTANCE_LOW
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    private Notification buildMediaNotification(String songTitle, Bitmap albumArt) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MEDIA_PLAYBACK_CHANNEL");
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        intent.setAction(NotificationReceiver.ACTION_PREV);
        PendingIntent prevPending = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        intent.setAction(NotificationReceiver.ACTION_PAUSE);
        PendingIntent pausePending = PendingIntent.getBroadcast(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        intent.setAction(NotificationReceiver.ACTION_NEXT);
        PendingIntent nextPending = PendingIntent.getBroadcast(this, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);



        builder.setSmallIcon(R.drawable.ic_launcher_background) // Replace with your app's icon
                .setContentTitle(songTitle)
                .setContentIntent(createContentIntent())
                .setLargeIcon(albumArt)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOnlyAlertOnce(true)
                .setAutoCancel(false)
                .setOngoing(true)
                .setShowWhen(false)
                .addAction(R.drawable.previous, "Previous", prevPending)
                .addAction(R.drawable.pause, "Pause", pausePending)
                .addAction(R.drawable.next, "Next", nextPending)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle());


        return builder.build();
    }

    private PendingIntent createContentIntent() {
        Intent openUI = new Intent(this, MainActivity.class);
        openUI.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(this, 0, openUI, 0);
    }

    private void showNotification(String songTitle, Bitmap albumArt) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, buildMediaNotification(songTitle, albumArt));
    }

    private void removeNotification() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.cancel(1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            stop();
        }
        removeNotification();
    }
}
