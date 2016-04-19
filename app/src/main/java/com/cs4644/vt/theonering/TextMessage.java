package com.cs4644.vt.theonering;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;

/**
 * Created by TheLoneBrick on 4/18/2016.
 */
public class TextMessage {
    final Uri SMS_INBOX = Uri.parse("content://sms/inbox");
    @SuppressWarnings("unused")
    private ContentResolver resolver;

    public TextMessage(ContentResolver conResolver) {
        resolver = conResolver;
    }

    public String getMess(int num) {
        Cursor cur = resolver.query(SMS_INBOX, null,null,null,null);
        String sms = "Message >> \n";
        int counter = 0;
        while (cur.moveToNext()) {
            sms += "From: " + cur.getString(2) + " : " + cur.getString(11)+"\n";
            if (counter == num) {
                break;
            }
            counter++;
        }
        return sms;
    }

    public int getUnrdMessCnt(){
        Cursor cur = resolver.query(SMS_INBOX, null, "read = 0", null, null);
        int unrdMessCnt = cur.getCount();
        cur.deactivate();
        return unrdMessCnt;
    }

    public String getMessAll(){
        Cursor cur = resolver.query(SMS_INBOX, null, null, null, null);
        String sms = "Message >> \n";
        while (cur.moveToNext()) {
            sms += "From: " + cur.getString(2) + " : " + cur.getString(11) + "\n";
        }
        return sms;
    }

    public String getMessUnrd(){
        Cursor cur = resolver.query(SMS_INBOX, null, "read = 0", null, null);
        String sms = "Message >> \n";
        while (cur.moveToNext()) {
            sms += "From: " + cur.getString(2) + " : " + cur.getString(11) + "\n";
        }
        return sms;
    }

    public void setMessageStatusRead() {
        ContentValues values = new ContentValues();
        values.put("read", true);
        resolver.update(SMS_INBOX, values, "_id=SmsMessageId", null);
    }
}
