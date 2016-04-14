package com.cs4644.vt.theonering;

/**
 * Created by Kelvin on 4/14/16.
 */
public class MusicManager {

    MainActivity m;

    public MusicManager(MainActivity mainActivity) {
        m = mainActivity;

    }

    public void pauseMusic() {
        m.toast("Pausing Music");

    }

    public void playMusic() {
        m.toast("Playing Music");

    }

    public void skipBackMusic() {
        m.toast("Skipping Back Music");

    }

    public void skipForwardMusic() {
        m.toast("Skipping Forward Music");

    }
}
