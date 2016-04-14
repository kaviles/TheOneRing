package com.cs4644.vt.theonering;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // This is a test comment please disregard.

    MusicManager mm;
    TextManager tm;
    WeatherManager wm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mm = new MusicManager(this);
        tm = new TextManager(this);
        wm = new WeatherManager(this);

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
                mm.skipBackMusic();

                break;
            case R.id.btn_music_play:
                mm.playMusic();

                break;
            case R.id.btn_music_pause:
                mm.pauseMusic();
                break;
            case R.id.btn_music_forward:
                mm.skipForwardMusic();
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
