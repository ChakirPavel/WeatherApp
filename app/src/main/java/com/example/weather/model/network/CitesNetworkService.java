package com.example.weather.model.network;


import com.example.weather.model.data.city.ResultCites;
import com.example.weather.model.network.general.BaseNetworkService;
import com.example.weather.model.network.retrofit.RetrofitCytes;

import io.reactivex.Single;


public class CitesNetworkService extends BaseNetworkService<RetrofitCytes> {

    private static final String idGoogleApi = "AIzaSyCIPdcVMd32J49rIPt84omoKnb1Xs6X3D0";

    @Override
    public String getApiLink() {
        return "https://maps.googleapis.com/maps/api/place/";
    }

    @Override
    public Class getRetrofitServiceClass() {
        return RetrofitCytes.class;
    }

    public Single<ResultCites> getCityes(String cityName){
        return retrofitService.getCites(cityName,  idGoogleApi);
    }
}