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
    private int activityState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mm = new MusicManager(this);
        tm = new TextManager(this);
        wm = new WeatherManager(this);
        activityState = 0;

        findViewById(R.id.btn_up).setOnClickListener(this);
        findViewById(R.id.btn_left).setOnClickListener(this);
        findViewById(R.id.btn_right).setOnClickListener(this);
        findViewById(R.id.btn_down).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();

        switch (i) {
            //When up arrow is pressed, cycle through activities.
            // 0 = music
            // 1 = text
            // 2 = weather
            case R.id.btn_up:
                if(activityState == 0)
                    activityState = 2;
                else
                    activityState--;
                break;

            case R.id.btn_left:
                if(activityState == 0)
                    mm.skipBackMusic();
                break;
            case R.id.btn_down:
                if(activityState == 0)
                    mm.playOrPauseMusic();
                if(activityState == 2)
                    this.toast("Reading Todays Weather");
                break;
            case R.id.btn_right:
                if(activityState == 0)
                    mm.skipForwardMusic();
                if(activityState == 1)
                    this.toast("Reading Text Message");
                if(activityState == 2)
                    this.toast("Reading Tomorrows Weather");
                break;
            default:
                break;
        }
    }

    public void toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
