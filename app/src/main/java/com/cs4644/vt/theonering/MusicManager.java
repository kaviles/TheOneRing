package com.cs4644.vt.theonering;

import android.media.MediaPlayer;

/**
 * Created by Kelvin on 4/14/16.
 */
public class MusicManager {

    MainActivity m;

    MediaPlayer[] mediaPlayer;
    int songCount;
    boolean isPlaying;

    public MusicManager(MainActivity mainActivity) {
        m = mainActivity;

        mediaPlayer = new MediaPlayer[3];
        mediaPlayer[0] = MediaPlayer.create(mainActivity, R.raw.song1);
        songCount = 0;
        isPlaying = false;
    }

    public void playOrPauseMusic() {
        if(!isPlaying) {
            mediaPlayer[songCount].start();
            isPlaying = true;
            m.toast("Playing Music");
        }
        else {
            mediaPlayer[songCount].pause();
            isPlaying = false;
            m.toast("Pausing Music");
        }

    }

    public void skipBackMusic() {
        m.toast("Skipping Back Music");

        mediaPlayer[songCount].stop();
        mediaPlayer[songCount].release();
        mediaPlayer[songCount] = null;
        isPlaying = false;

        if (songCount == 0) {
            songCount = 2;
            mediaPlayer[songCount] = MediaPlayer.create(m, R.raw.song3);
        } else if (songCount == 2) {
            songCount--;
            mediaPlayer[songCount] = MediaPlayer.create(m, R.raw.song2);
        } else if (songCount == 1) {
            songCount--;
            mediaPlayer[songCount] = MediaPlayer.create(m, R.raw.song1);
        }

        mediaPlayer[songCount].start();
        isPlaying = true;
    }

    public void skipForwardMusic() {
        m.toast("Skipping Forward Music");

        mediaPlayer[songCount].stop();
        mediaPlayer[songCount].release();
        mediaPlayer[songCount] = null;


        //this.onDestroy();
        if (songCount == 0) {
            songCount++;
            mediaPlayer[songCount] = MediaPlayer.create(m, R.raw.song2);
        } else if (songCount == 1) {
            songCount++;
            mediaPlayer[songCount] = MediaPlayer.create(m, R.raw.song3);
        } else {
            songCount = 0;
            mediaPlayer[songCount] = MediaPlayer.create(m, R.raw.song1);
        }

        mediaPlayer[songCount].start();
        isPlaying = true;
    }
}
