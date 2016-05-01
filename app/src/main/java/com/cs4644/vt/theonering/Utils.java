package com.cs4644.vt.theonering;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Kelvin on 4/29/16.
 */
public class Utils {

    static public void toast(AppCompatActivity activity, String s) {
        Toast.makeText(activity.getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    static public String asciiToHex(String text) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            String charString = String.format("0x%02X", (byte) text.charAt(i));

            stringBuffer.append(charString + " ");
        }
        return stringBuffer.toString();
    }

    static public String getCurrentDateTimestamp(long time) {
        return DateFormat.getTimeInstance().format(new Date(time));
    }
}
