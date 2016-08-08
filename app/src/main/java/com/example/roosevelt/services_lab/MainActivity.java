package com.example.roosevelt.services_lab;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.roosevelt.services_lab.services.MusicStreamService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button play, pause, stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();

        if(hasInternetConnection()){
            Intent intent = new Intent(MainActivity.this, MusicStreamService.class);
            startService(intent);
        }
        else
            Toast.makeText(MainActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
    }

    void setViews(){
        play = (Button) findViewById(R.id.btnPlay);
        pause = (Button) findViewById(R.id.btnPause);
        stop = (Button) findViewById(R.id.btnStop);

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnPlay:
                MusicStreamService.playMediaPlayer();
                break;
            case R.id.btnPause:
                MusicStreamService.pauseMediaPlayer();
                break;
            case R.id.btnStop:
                MusicStreamService.stopMediaPlayer();
//                stopService(new Intent(MainActivity.this, MusicStreamService.class));
                break;
        }
    }

    boolean hasInternetConnection(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(new Intent(MainActivity.this, MusicStreamService.class));
    }
}
