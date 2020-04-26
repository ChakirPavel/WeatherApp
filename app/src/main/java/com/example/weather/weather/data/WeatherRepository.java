package com.example.weather.weather.data;

import com.example.weather.model.data.city.ResultCites;
import com.example.weather.model.data.weather.Weather;
import com.example.weather.model.network.CitesNetworkService;
import com.example.weather.model.network.WeatherNetworkService;

import io.reactivex.Single;

public class WeatherRepository {
    private CitesNetworkService citesNetworkService = new CitesNetworkService();
    private WeatherNetworkService weatherNetworkService = new WeatherNetworkService();

    public Single<ResultCites> getCityesByName(String cityName){
        return citesNetworkService.getCityes(cityName);
    }

    public Single<Weather> getWeather(Float lat, Float lng){
        return weatherNetworkService.getWeatherByCityName(lat, lng);
    }
}
