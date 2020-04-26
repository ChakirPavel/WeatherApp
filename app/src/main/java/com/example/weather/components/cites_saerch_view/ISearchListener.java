package com.example.weather.components.cites_saerch_view;

public interface ISearchListener {
    void onSearchTextChanged(String query);
    void onSelectCity(Float lat, Float lng, String nameCity);
}
