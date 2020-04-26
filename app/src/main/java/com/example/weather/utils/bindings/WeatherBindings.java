package com.example.weather.utils.bindings;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.example.weather.R;
import com.example.weather.model.data.weather.Weather;

public class WeatherBindings {

    @BindingAdapter("tempCelsius")
    public static void setTemp(TextView textView, Weather weather) {
        if(weather != null){
            textView.setText(textView.getContext().getString(R.string.temp_celsius, weather.getTemp()));
        }
    }

    @BindingAdapter("feelLike")
    public static void setFeelLike(TextView textView, Weather weather) {
        if(weather != null){
            textView.setText(textView.getContext().getString(R.string.feel_like, weather.getFeelsLike()));
        }
    }

    @BindingAdapter("weather")
    public static void setWeather(TextView textView, Weather weather) {
        if(weather != null){
            textView.setText(textView.getContext().getString(R.string.weather_description, weather.getWeatherInfo()));
        }
    }
}
