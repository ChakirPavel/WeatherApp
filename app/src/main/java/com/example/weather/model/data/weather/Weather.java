package com.example.weather.model.data.weather;

import java.util.ArrayList;

public class Weather {
    public WeatherInfo fact;

    public String getTemp() {
        return fact.temp.toString();
    }
    public String getWeatherInfo() {
        return fact.condition;
    }
    public String getFeelsLike() {
        return fact.feels_like.toString();
    }

}
