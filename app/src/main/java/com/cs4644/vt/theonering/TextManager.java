package com.cs4644.vt.theonering;

import java.util.Random;

/**
 * Created by Kelvin on 4/14/16.
 */
public class TextManager {

    MainActivity m;

    String text1 = "From David: can you pick up the pizzas from dominos";
    String text2 = "From Bob: when does the movie start?";
    String text3 = "From Jane: Are we meeting there?";
    String text4 = "From Eliza: what time is your flight coming in?";
    String text5 = "From Billy: does 4:00 still work";
    String text6 = "From James: when water buffalo fly";
    String text7 = "From Henry: wanna play a game??";
    String text8 = "From Nick: what time should I pick you up?";
    String text9 = "From Peter: I can't find any...";
    Random rand;


    public TextManager(MainActivity mainActivity) {
        m = mainActivity;

        rand = new Random();

    }

    public String readTexts() {
        m.toast("Reading Texts");

        return readText(rand.nextInt(9));
    }

    public String readText(int i) {
        switch (i) {
            case 1: return text1;
            case 2: return text2;
            case 3: return text3;
            case 4: return text4;
            case 5: return text5;
            case 6: return text6;
            case 7: return text7;
            case 8: return text8;
            case 9: return text9;
            default: return "No unread messages found.";
        }

    }
}
