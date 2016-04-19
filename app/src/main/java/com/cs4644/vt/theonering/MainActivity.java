package com.cs4644.vt.theonering;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // This is a test comment please disregard.

    MusicManager mm;
    TextManager tm;
    WeatherManager wm;
    MediaPlayer[] mediaPlayer;
    int songCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mm = new MusicManager(this);
        tm = new TextManager(this);
        wm = new WeatherManager(this);
        mediaPlayer = new MediaPlayer[3];
        mediaPlayer[0] = MediaPlayer.create(MainActivity.this, R.raw.song1);
        songCount = 0;
        findViewById(R.id.btn_music_back).setOnClickListener(this);
        findViewById(R.id.btn_music_play).setOnClickListener(this);
        findViewById(R.id.btn_music_pause).setOnClickListener(this);
        findViewById(R.id.btn_music_forward).setOnClickListener(this);
        findViewById(R.id.btn_read_texts).setOnClickListener(this);
        findViewById(R.id.btn_read_weather).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int i = v.getId();

        switch (i) {
            case R.id.btn_music_back:
                mediaPlayer[songCount].stop();
                mediaPlayer[songCount].release();
                mediaPlayer[songCount] = null;
                if (songCount == 0) {
                    songCount = 2;
                    mediaPlayer[songCount] = MediaPlayer.create(MainActivity.this, R.raw.song3);
                } else if (songCount == 2) {
                    songCount--;
                    mediaPlayer[songCount] = MediaPlayer.create(MainActivity.this, R.raw.song2);
                } else if (songCount == 1) {
                    songCount--;
                    mediaPlayer[songCount] = MediaPlayer.create(MainActivity.this, R.raw.song1);
                }
                mediaPlayer[songCount].start();
                mm.skipBackMusic();
                break;
            case R.id.btn_music_play:
                mm.playMusic();
                mediaPlayer[songCount].start();
                break;
            case R.id.btn_music_pause:
                mediaPlayer[songCount].pause();
                mm.pauseMusic();
                break;
            case R.id.btn_music_forward:
                mm.skipForwardMusic();
                mediaPlayer[songCount].stop();
                mediaPlayer[songCount].release();
                mediaPlayer[songCount] = null;
                //this.onDestroy();
                if (songCount == 0) {
                    songCount++;
                    mediaPlayer[songCount] = MediaPlayer.create(MainActivity.this, R.raw.song2);
                } else if (songCount == 1) {
                    songCount++;
                    mediaPlayer[songCount] = MediaPlayer.create(MainActivity.this, R.raw.song3);
                } else {
                    songCount = 0;
                    mediaPlayer[songCount] = MediaPlayer.create(MainActivity.this, R.raw.song1);
                }
                mediaPlayer[songCount].start();
                break;
            case R.id.btn_read_texts:
                tm.readTexts();
                break;
            case R.id.btn_read_weather:
                wm.readWeather();
                break;
            default:
                break;
        }
    }

    public void toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
