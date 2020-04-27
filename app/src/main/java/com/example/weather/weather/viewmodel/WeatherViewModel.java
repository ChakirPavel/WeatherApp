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
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends ViewModel {
    private WeatherRepository weatherRepository;
    public MutableLiveData<ResultNetwork<List<City>>> listCities = new MutableLiveData<>();
    public MutableLiveData<ResultNetwork<Weather>> cityWeather = new MutableLiveData<>();
    public MutableLiveData<String> nameCity = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    WeatherViewModel(WeatherRepository weatherRepository){
        this.weatherRepository = weatherRepository;
    }

    public void updateCities(String nameCity){
        compositeDisposable.add(weatherRepository.getCitiesByName(nameCity)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> listCities.postValue(ResultNetwork.loading()))
                .subscribe(
                        result -> listCities.setValue(ResultNetwork.success(((ResultCities)result).results )),
                        throwable -> listCities.setValue(ResultNetwork.error((Throwable)throwable))
                ));
    }

    public void loadCityInfo(Float lat, Float lng, String nameCity){
        this.nameCity.postValue(nameCity);
        compositeDisposable.add(weatherRepository.getWeather(lat, lng)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> cityWeather.postValue(ResultNetwork.loading()))
                .subscribe(
                        result -> cityWeather.setValue(ResultNetwork.success(result)),
                        throwable -> cityWeather.setValue(ResultNetwork.error((Throwable)throwable))
                ));
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
