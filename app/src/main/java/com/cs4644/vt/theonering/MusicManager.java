package com.cs4644.vt.theonering;

import android.media.MediaPlayer;

/**
 * Created by Kelvin on 4/14/16.
 */
public class MusicManager {

    MainActivity m;

    MediaPlayer[] mediaPlayer;
    int songCount;

    public MusicManager(MainActivity mainActivity) {
        m = mainActivity;

        mediaPlayer = new MediaPlayer[3];
        mediaPlayer[0] = MediaPlayer.create(mainActivity, R.raw.song1);
        songCount = 0;
    }

    public void pauseMusic() {
        m.toast("Pausing Music");

        mediaPlayer[songCount].pause();
    }

    public void playMusic() {
        m.toast("Playing Music");

        mediaPlayer[songCount].start();
    }

    public void skipBackMusic() {
        m.toast("Skipping Back Music");

        mediaPlayer[songCount].stop();
        mediaPlayer[songCount].release();
        mediaPlayer[songCount] = null;

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
    }
}
