package com.example.weather.model.network;


import com.example.weather.model.data.city.ResultCities;
import com.example.weather.model.network.general.BaseNetworkService;
import com.example.weather.model.network.retrofit.RetrofitCities;

import io.reactivex.Single;


public class CitiesNetworkService extends BaseNetworkService<RetrofitCities> {

    private static final String idGoogleApi = "AIzaSyCIPdcVMd32J49rIPt84omoKnb1Xs6X3D0";

    @Override
    public String getApiLink() {
        return "https://maps.googleapis.com/maps/api/place/";
    }

    @Override
    public Class getRetrofitServiceClass() {
        return RetrofitCities.class;
    }

    public Single<ResultCities> getCities(String cityName){
        return retrofitService.getCities(cityName,  idGoogleApi);
    }
}