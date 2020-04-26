package com.example.weather.components.cities_search_view;

public interface ISearchListener {
    void onSearchTextChanged(String query);
    void onSelectCity(Float lat, Float lng, String nameCity);
}
