package com.example.weather.utils;

import com.example.weather.weather.viewmodel.WeatherViewModelFactory;
import android.content.Context;

public class Injection {

    public static WeatherViewModelFactory provideViewModelFactory(Context context) {
        return new WeatherViewModelFactory();
    }
}
