package com.example.weather.weather.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.weather.weather.data.WeatherRepository;

public class WeatherViewModelFactory implements ViewModelProvider.Factory {

    private final WeatherRepository weatherRepository = new WeatherRepository();


    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WeatherViewModel.class)) {
            return (T) new WeatherViewModel(weatherRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

