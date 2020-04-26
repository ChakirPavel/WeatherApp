package com.example.weather.model.network.retrofit;

import com.example.weather.model.data.weather.Weather;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface RetrofitWeather {
    @Headers({"User-Agent: CityWeatherApp", "X-Yandex-API-Key: 42441eb0-dfff-4fa4-a2df-18254d8b921c"})
    @GET("forecast")
    Single<Weather> getWeather(@Query("lat") Float lat, @Query("lon") Float lon);
}
