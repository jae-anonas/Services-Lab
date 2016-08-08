package com.example.roosevelt.services_lab.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by roosevelt on 8/8/16.
 */
public class MusicStreamService extends Service {
    public static final String MUSIC_URL = "http://wapclash.com/mp3/download-file.php?id=173189350";
    private static final String TAG = "MusicStreamService";

    private static MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = null;
        String url = MUSIC_URL; // your URL here
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG, TAG + " Created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service Started, should do some work");

        // create thread and pass in a runnable task
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mediaPlayer.prepare(); // might take long! (for buffering, etc)

                    Log.i(TAG,"Service is done working!");

                } catch (IOException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });

        // start the thread
        thread.start();

        // toast after the "work" was finished
        Toast.makeText(getApplicationContext(), "Service work is happening", Toast.LENGTH_SHORT).show();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service Destroyed");
        mediaPlayer.stop();
        mediaPlayer.release();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
    }

    public static void playMediaPlayer(){
        if (mediaPlayer != null){
            mediaPlayer.start();
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
    }

    public static void pauseMediaPlayer(){
        if (mediaPlayer != null){
            mediaPlayer.pause();
        }
    }

    public static void stopMediaPlayer(){
        if (mediaPlayer != null){
//            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }
}
