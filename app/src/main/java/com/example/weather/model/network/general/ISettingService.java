package com.example.weather.model.network.general;

// I don't install Java 9+, so, i can't add private modification for interface methods
public interface ISettingService<T> {
    String getApiLink();
    Class<T> getRetrofitServiceClass();
}
