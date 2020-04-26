package com.example.weather.weather.viewmodel;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather.model.data.city.City;
import com.example.weather.model.data.city.ResultCities;
import com.example.weather.model.data.weather.Weather;
import com.example.weather.utils.ResultNetwork;
import com.example.weather.weather.data.WeatherRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends ViewModel {
    private WeatherRepository weatherRepository;
    public MutableLiveData<ResultNetwork<List<City>>> listCites = new MutableLiveData<>();
    public MutableLiveData<ResultNetwork<Weather>> cityWeather = new MutableLiveData<>();
    public MutableLiveData<String> nameCity = new MutableLiveData<>();


    WeatherViewModel(WeatherRepository weatherRepository){
        this.weatherRepository = weatherRepository;
    }

    @SuppressLint("CheckResult")
    public void updateCities(String nameCity){
        weatherRepository.getCitiesByName(nameCity)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> listCites.postValue(ResultNetwork.loading()))
                .subscribe(
                        result -> listCites.setValue(ResultNetwork.success(((ResultCities)result).results )),
                        throwable -> listCites.setValue(ResultNetwork.error((Throwable)throwable))
                );
    }

    @SuppressLint("CheckResult")
    public void loadCityInfo(Float lat, Float lng, String nameCity){
        this.nameCity.postValue(nameCity);
        weatherRepository.getWeather(lat, lng)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> cityWeather.postValue(ResultNetwork.loading()))
                .subscribe(
                        result -> cityWeather.setValue(ResultNetwork.success(result)),
                        throwable -> cityWeather.setValue(ResultNetwork.error((Throwable)throwable))
                );
    }
}
