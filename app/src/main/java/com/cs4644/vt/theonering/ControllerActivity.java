package com.cs4644.vt.theonering;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs4644.vt.theonering.ble.BleManager;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Locale;

public class ControllerActivity extends UartInterfaceActivity
        implements View.OnClickListener, BleManager.BleManagerListener, TextToSpeech.OnInitListener { //, MqttManager.MqttManagerListener {

    // Log
    private final static String TAG = ControllerActivity.class.getSimpleName();

    // Constants
    private static final int kNumPublishFeeds = 2;
    public static final int kPublishFeed_RX = 0;
    public static final int kPublishFeed_TX = 1;

//    private MqttManager mMqttManager;

    // Data
    private static final boolean mShowDataInHexFormat = false;
    private static final boolean mIsEolEnabled = true;

    private volatile SpannableStringBuilder mTextSpanBuffer;
    private volatile ArrayList<UartDataChunk> mDataBuffer;
    private volatile int mSentBytes;
    private volatile int mReceivedBytes;

    private TextView tv;

    private Button btn_up;
    private Button btn_left;
    private Button btn_right;
    private Button btn_down;

    MusicManager mm;
    TextManager tm;
    WeatherManager wm;
    private int activityState;
    private TextToSpeech tts;
    private boolean ttsInitialized;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        mm = new MusicManager(this);
        tm = new TextManager(this);
        wm = new WeatherManager(this);
        activityState = 0;
        tts = new TextToSpeech(this, this);

        btn_up = (Button) findViewById(R.id.btn_up);
        btn_up.setOnClickListener(this);
        btn_up.setVisibility(View.GONE);
        btn_left = (Button) findViewById(R.id.btn_left);
        btn_left.setOnClickListener(this);
        btn_left.setVisibility(View.GONE);
        btn_right = (Button) findViewById(R.id.btn_right);
        btn_right.setOnClickListener(this);
        btn_right.setVisibility(View.GONE);
        btn_down = (Button) findViewById(R.id.btn_down);
        btn_down.setOnClickListener(this);
        btn_down.setVisibility(View.GONE);

        tv = (TextView) findViewById(R.id.textView);
        tv.setVisibility(View.GONE);

        mBleManager = BleManager.getInstance(this);

        mSentBytes = 0;
        mReceivedBytes = 0;

        mDataBuffer = new ArrayList<>();
        mTextSpanBuffer = new SpannableStringBuilder();

        // Continue
        onServicesDiscovered();

        // Mqtt init
//        mMqttManager = MqttManager.getInstance(this);
//        if (MqttSettings.getInstance(this).isConnected()) {
//            mMqttManager.connectFromSavedSettings(this);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Setup listeners
        mBleManager.setBleListener(this);

//        mMqttManager.setListener(this);
//        updateMqttStatus();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        // Disconnect mqtt
//        if (mMqttManager != null) {
//            mMqttManager.disconnect();
//        }

        // Retain data
//        saveRetainedDataFragment();

        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

//        int i = v.getId();
//
//        switch (i) {
//            //When up arrow is pressed, cycle through activities.
//            // 0 = music
//            // 1 = text
//            // 2 = weather
//            case R.id.btn_up:
//                if(activityState == 0) {
//                    activityState = 2;
//                    speakSomething("Weather");
//                }
//                else if (activityState == 1) {
//                    activityState--;
//                    speakSomething("Music");
//                }
//                else if (activityState == 2){
//                    activityState--;
//                    speakSomething("Text Messages");
//                }
//
//                break;
//
//            case R.id.btn_left:
//                if(activityState == 0)
//                    mm.skipBackMusic();
//                break;
//            case R.id.btn_down:
//                if(activityState == 0)
//                    mm.playOrPauseMusic();
//                if(activityState == 2) {
//                    Utils.toast(this, "Reading Today's Weather");
//                    speakSomething(wm.getTodaysWeather());
//                }
//                break;
//            case R.id.btn_right:
//                if(activityState == 0)
//                    mm.skipForwardMusic();
//                if(activityState == 1)
//                    speakSomething(tm.readTexts());
//                if(activityState == 2) {
//                    Utils.toast(this, "Reading Tomorrows Weather");
//                    speakSomething(wm.getTomorrowsWeather());
//                }
//                break;
//            default:
//                break;
//        }
    }

    public void handleMenuOption(String val) {

        switch (val) {
            //When up arrow is pressed, cycle through activities.
            // 0 = music
            // 1 = text
            // 2 = weather
            case "U":
                if (activityState == 0) {
                    activityState = 2;
                    mm.pauseMusic();
                    speakSomething("Weather");
                } else if (activityState == 1) {
                    activityState--;
                    speakSomething("Music");
                } else if (activityState == 2) {
                    activityState--;
                    speakSomething("Text Messages");
                }
                break;
            case "L":
                if (activityState == 0)
                    mm.skipBackMusic();
                break;
            case "D":
                if (activityState == 0)
                    mm.playOrPauseMusic();
                if (activityState == 2) {
                    Utils.toast(this, "Reading Today's Weather");
                    speakSomething(wm.getTodaysWeather());
                }
                break;
            case "R":
                if (activityState == 0)
                    mm.skipForwardMusic();
                if (activityState == 1)
                    speakSomething(tm.readTexts());
                if (activityState == 2) {
                    Utils.toast(this, "Reading Tomorrows Weather");
                    speakSomething(wm.getTomorrowsWeather());
                }
                break;
            default:
                break;
        }
    }

//    private void updateMqttStatus() {
//        if (mMqttMenuItem == null) return;      // Hack: Sometimes this could have not been initialized so we don't update icons

//        MqttManager mqttManager = mMqttManager.getInstance(this);
//        MqttManager.MqqtConnectionStatus status = mqttManager.getClientStatus();


//        if (status == MqttManager.MqqtConnectionStatus.CONNECTING) {
//            final int kConnectingAnimationDrawableIds[] = {R.drawable.mqtt_connecting1, R.drawable.mqtt_connecting2, R.drawable.mqtt_connecting3};
//            mMqttMenuItem.setIcon(kConnectingAnimationDrawableIds[mMqttMenuItemAnimationFrame]);
//            mMqttMenuItemAnimationFrame = (mMqttMenuItemAnimationFrame + 1) % kConnectingAnimationDrawableIds.length;
//        }
//        else if (status == MqttManager.MqqtConnectionStatus.CONNECTED) {
//            mMqttMenuItem.setIcon(R.drawable.mqtt_connected);
//            mMqttMenuItemAnimationHandler.removeCallbacks(mMqttMenuItemAnimationRunnable);
//        }
//        else {
//            mMqttMenuItem.setIcon(R.drawable.mqtt_disconnected);
//            mMqttMenuItemAnimationHandler.removeCallbacks(mMqttMenuItemAnimationRunnable);
//        }
//    }

    private void uartSendData(String data, boolean wasReceivedFromMqtt) {

        // MQTT publish to TX
//        MqttSettings settings = MqttSettings.getInstance(ControllerActivity.this);
//        if (!wasReceivedFromMqtt) {
//            if (settings.isPublishEnabled()) {
//                String topic = settings.getPublishTopic(kPublishFeed_TX);
//                final int qos = settings.getPublishQos(kPublishFeed_TX);
//                mMqttManager.publish(topic, data, qos);
//            }
//        }

        // Add eol
        if (mIsEolEnabled) {
            // Add newline character if checked
            data += "\n";
        }

        // Send to uart
//        if (!wasReceivedFromMqtt || settings.getSubscribeBehaviour() == MqttSettings.kSubscribeBehaviour_Transmit) {
            sendData(data);
            mSentBytes += data.length();
//        }

        // Add to current buffer
        UartDataChunk dataChunk = new UartDataChunk(System.currentTimeMillis(), UartDataChunk.TRANSFERMODE_TX, data);
        mDataBuffer.add(dataChunk);

//        final String formattedData = mShowDataInHexFormat ? Utils.asciiToHex(data) : data;
//        if (mIsTimestampDisplayMode) {
//            final String currentDateTimeString = DateFormat.getTimeInstance().format(new Date(dataChunk.getTimestamp()));
//            mBufferListAdapter.add(new TimestampData("[" + currentDateTimeString + "] TX: " + formattedData, mTxColor));
//            mBufferListView.setSelection(mBufferListAdapter.getCount());
//        }
//
//        // Update UI
//        updateUI();
    }

    @Override
    public synchronized void onDataAvailable(BluetoothGattCharacteristic characteristic) {
        // UART RX
        if (characteristic.getService().getUuid().toString().equalsIgnoreCase(UUID_SERVICE)) {
            if (characteristic.getUuid().toString().equalsIgnoreCase(UUID_RX)) {
                final byte[] bytes = characteristic.getValue();
                final String data = new String(bytes, Charset.forName("UTF-8"));

                mReceivedBytes += bytes.length;

                final UartDataChunk dataChunk = new UartDataChunk(System.currentTimeMillis(), UartDataChunk.TRANSFERMODE_RX, data);
                mDataBuffer.add(dataChunk);
                final String formattedData = mShowDataInHexFormat ? Utils.asciiToHex(data) : data;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        if (mIsTimestampDisplayMode) {
//                            final String currentDateTimeString = DateFormat.getTimeInstance().format(new Date(dataChunk.getTimestamp()));
//
//                            mBufferListAdapter.add(new TimestampData("[" + currentDateTimeString + "] TX: " + formattedData, mRxColor));
//                            //mBufferListAdapter.add("[" + currentDateTimeString + "] RX: " + formattedData);
//                            //mBufferListView.smoothScrollToPosition(mBufferListAdapter.getCount() - 1);
//                            mBufferListView.setSelection(mBufferListAdapter.getCount());
//                        }
//                        updateUI();


                        // Send data to handleMenuOptions()
                        handleMenuOption(formattedData);
                    }
                });

//                // MQTT publish to RX
//                MqttSettings settings = MqttSettings.getInstance(ControllerActivity.this);
//                if (settings.isPublishEnabled()) {
//                    String topic = settings.getPublishTopic(kPublishFeed_RX);
//                    final int qos = settings.getPublishQos(kPublishFeed_RX);
//                    mMqttManager.publish(topic, data, qos);
//                }
            }
        }
    }

    // region BleManagerListener
    @Override
    public void onConnected() {

    }

    @Override
    public void onConnecting() {

    }

    @Override
    public void onDisconnected() {
        Log.d(TAG, "Disconnected. Back to previous activity");
        finish();
    }

    @Override
    public void onServicesDiscovered() {
        mUartService = mBleManager.getGattService(UUID_SERVICE);

        mBleManager.enableNotification(mUartService, UUID_RX, true);
    }

    @Override
    public void onDataAvailable(BluetoothGattDescriptor descriptor) {

    }

    @Override
    public void onReadRemoteRssi(int rssi) {

    }

    @Override
    public void onInit(int status) {
        if (status  == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.ENGLISH);
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This language isn't supported");
            } else {
                ttsInitialized = true;
            }
        } else {
            Log.e("TTS", "Initialization Failed");
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void speakSomething(String speak) {
        if (!ttsInitialized) {
            Log.e("TTS", "TextToSpeech Was Not Initialized");
            return;
        }
        tts.speak(speak, TextToSpeech.QUEUE_FLUSH, null, "speak");
    }

    // endregion

//    // region MqttManagerListener
//    @Override
//    public void onMqttConnected() {
//        updateMqttStatus();
//    }
//
//    @Override
//    public void onMqttDisconnected() {
//        updateMqttStatus();
//    }
//
//    @Override
//    public void onMqttMessageArrived(String topic, MqttMessage mqttMessage) {
//        final String message = new String(mqttMessage.getPayload());
//
//        Log.d(TAG, "Mqtt messageArrived from topic: " +topic+ " message: "+message);
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                uartSendData(message, true);       // Don't republish to mqtt something received from mqtt
//            }
//        });
//    }
    // endregion
}
