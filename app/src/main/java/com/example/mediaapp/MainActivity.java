package com.example.mediaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;

import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private ImageButton play;
    public TextView duration;
    public String s,a;
    public String q,w;
    private TextView current;
    public SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer= MediaPlayer.create(this,R.raw.mileyani);
        play=findViewById(R.id.play);
        duration=findViewById(R.id.duration);
        current=findViewById(R.id.current);

        int minmax =(mediaPlayer.getDuration()/1000)/60;
        int secmax =(mediaPlayer.getDuration()/1000)%60;
        if(minmax <10){
            s= "0"+minmax;
        }
        else{
            s= Integer.toString(minmax);

        }
        if(secmax <10){
            a="0"+ secmax;
        }
        else{
            a= Integer.toString(secmax);

        }




       
        duration.setText(s+":"+a);
        seekBar=findViewById(R.id.seekBar);
        seekBar.setMax(mediaPlayer.getDuration());

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);


            }
        });
        Thread mthread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int mincurr =(mediaPlayer.getCurrentPosition()/1000)/60;
                                int seccurr =(mediaPlayer.getCurrentPosition()/1000)%60;
                                if(mincurr <10){
                                    q= "0"+mincurr;
                                }
                                else{
                                    q= Integer.toString(mincurr);

                                }
                                if(seccurr <10){
                                    w="0"+ seccurr;
                                }
                                else{
                                    w= Integer.toString(seccurr);

                                }
                                current.setText(q+":"+w);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        mthread.start();
        Handler mHandler = new Handler();
//Make sure you update Seekbar on UI thread
        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if(mediaPlayer != null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(mCurrentPosition);
                }
                mHandler.postDelayed(this, 1000);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b){
                    mediaPlayer.seekTo(i);}
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    play.setImageResource(R.drawable.pause1);
                    mediaPlayer.pause();
                }
                else{
                    play.setImageResource(R.drawable.play1);
                    mediaPlayer.start();

                }
            }
        });

    }

}