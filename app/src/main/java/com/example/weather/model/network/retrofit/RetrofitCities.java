package com.example.weather.model.network.retrofit;

import com.example.weather.model.data.city.ResultCities;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RetrofitCities {
    @Headers("User-Agent: CityWeatherApp")
    @GET("textsearch/json")
    Single<ResultCities> getCities(@Query("query") String cityName, @Query("key") String key);
}
