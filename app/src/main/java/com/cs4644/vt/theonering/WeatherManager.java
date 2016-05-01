package com.cs4644.vt.theonering;

/**
 * Created by Kelvin on 4/14/16.
 */
public class WeatherManager {

    ControllerActivity ca;
    private String todayWeather;
    private String tmrwWeather;

    public WeatherManager(ControllerActivity mainActivity) {

        ca = mainActivity;
        todayWeather = "Today will have a high of 64 and lows in the fifties, " +
                "It will be cloudy with a 50 percent chance of rain during the evening";
        tmrwWeather = "Tomorrows weather will have a high of 69 and lows in the fifties" +
                "It will be sunny all day, with scattered clouds during the evening";
    }

    public void readWeather() {
        Utils.toast(ca, "Reading Weather");
    }

    public String getTodaysWeather() {
        return todayWeather;
    }

    public String getTomorrowsWeather() {
        return tmrwWeather;
    }
}
