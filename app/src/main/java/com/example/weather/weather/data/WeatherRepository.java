package com.example.weather.weather.data;

import com.example.weather.model.data.city.ResultCities;
import com.example.weather.model.data.weather.Weather;
import com.example.weather.model.network.CitiesNetworkService;
import com.example.weather.model.network.WeatherNetworkService;

import io.reactivex.Single;

public class WeatherRepository {
    private CitiesNetworkService citiesNetworkService = new CitiesNetworkService();
    private WeatherNetworkService weatherNetworkService = new WeatherNetworkService();

    public Single<ResultCities> getCitiesByName(String cityName){
        return citiesNetworkService.getCities(cityName);
    }

    public Single<Weather> getWeather(Float lat, Float lng){
        return weatherNetworkService.getWeatherByCityName(lat, lng);
    }
}
