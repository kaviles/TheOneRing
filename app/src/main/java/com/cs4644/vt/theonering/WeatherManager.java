package com.cs4644.vt.theonering;

/**
 * Created by Kelvin on 4/14/16.
 */
public class WeatherManager {

    MainActivity m;

    public WeatherManager(MainActivity mainActivity) {

        m = mainActivity;
    }

    public void readWeather() {
        m.toast("Reading Weather");
    }


    public void getWeatherData() {
        // pull weather data from Weather Channel website or some other weather service API
    }
}
