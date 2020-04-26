package com.example.weather.model.network.retrofit;

import com.example.weather.model.data.city.ResultCites;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RetrofitCytes {
    @Headers("User-Agent: CityWeatherApp")
    @GET("textsearch/json")
    Single<ResultCites> getCities(@Query("query") String cityName, @Query("key") String key);
}
