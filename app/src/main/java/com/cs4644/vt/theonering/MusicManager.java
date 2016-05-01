package com.cs4644.vt.theonering;

import android.media.MediaPlayer;

/**
 * Created by Kelvin on 4/14/16.
 */
public class MusicManager {

    ControllerActivity ca;

    MediaPlayer[] mediaPlayer;
    int songCount;
    boolean isPlaying;

    public MusicManager(ControllerActivity controllerActivity) {
        ca = controllerActivity;

        mediaPlayer = new MediaPlayer[3];
        mediaPlayer[0] = MediaPlayer.create(ca, R.raw.song1);
        songCount = 0;
        isPlaying = false;
    }

    public void playOrPauseMusic() {
        if(!isPlaying) {
            mediaPlayer[songCount].start();
            isPlaying = true;
            Utils.toast(ca, "Playing Music");
        }
        else {
            mediaPlayer[songCount].pause();
            isPlaying = false;
            Utils.toast(ca, "Pausing Music");
        }

    }

    public void pauseMusic() {
        if (isPlaying) {
            mediaPlayer[songCount].pause();
            isPlaying = false;
        }
    }

    public void skipBackMusic() {
        Utils.toast(ca, "Skipping Back Music");

        mediaPlayer[songCount].stop();
        mediaPlayer[songCount].release();
        mediaPlayer[songCount] = null;
        isPlaying = false;

        if (songCount == 0) {
            songCount = 2;
            mediaPlayer[songCount] = MediaPlayer.create(ca, R.raw.song3);
        } else if (songCount == 2) {
            songCount--;
            mediaPlayer[songCount] = MediaPlayer.create(ca, R.raw.song2);
        } else if (songCount == 1) {
            songCount--;
            mediaPlayer[songCount] = MediaPlayer.create(ca, R.raw.song1);
        }

        mediaPlayer[songCount].start();
        isPlaying = true;
    }

    public void skipForwardMusic() {
        Utils.toast(ca, "Skipping Forward Music");

        mediaPlayer[songCount].stop();
        mediaPlayer[songCount].release();
        mediaPlayer[songCount] = null;

        //this.onDestroy();
        if (songCount == 0) {
            songCount++;
            mediaPlayer[songCount] = MediaPlayer.create(ca, R.raw.song2);
        } else if (songCount == 1) {
            songCount++;
            mediaPlayer[songCount] = MediaPlayer.create(ca, R.raw.song3);
        } else {
            songCount = 0;
            mediaPlayer[songCount] = MediaPlayer.create(ca, R.raw.song1);
        }

        mediaPlayer[songCount].start();
        isPlaying = true;
    }
}
