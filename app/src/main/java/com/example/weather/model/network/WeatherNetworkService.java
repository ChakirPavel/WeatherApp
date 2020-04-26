package com.example.weather.model.network;

import com.example.weather.model.data.weather.Weather;
import com.example.weather.model.network.general.BaseNetworkService;
import com.example.weather.model.network.retrofit.RetrofitWeather;

import io.reactivex.Single;

public class WeatherNetworkService extends BaseNetworkService<RetrofitWeather> {
    @Override
    public Class getRetrofitServiceClass() {
        return RetrofitWeather.class;
    }

    public Single<Weather> getWeatherByCityName(Float lat, Float lng){
        return retrofitService.getWeather(lat, lng);
    }
}
