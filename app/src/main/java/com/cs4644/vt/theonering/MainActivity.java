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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mm = new MusicManager(this);
        tm = new TextManager(this);
        wm = new WeatherManager(this);

        findViewById(R.id.btn_up).setOnClickListener(this);
        findViewById(R.id.btn_left).setOnClickListener(this);
        findViewById(R.id.btn_right).setOnClickListener(this);
        findViewById(R.id.btn_down).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();

        switch (i) {
            case R.id.btn_left:
                mm.skipBackMusic();
                break;
            case R.id.btn_up:
                mm.playMusic();
                break;
            case R.id.btn_down:
                mm.pauseMusic();
                break;
            case R.id.btn_right:
                mm.skipForwardMusic();
                break;
            default:
                break;
        }
    }

    public void toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
