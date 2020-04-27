package com.example.weather.weather.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.weather.R;
import com.example.weather.components.cities_search_view.ISearchListener;
import com.example.weather.databinding.ActivityWeatherBinding;
import com.example.weather.general.BaseActivity;
import com.example.weather.utils.Injection;
import com.example.weather.utils.ResultType;
import com.example.weather.weather.viewmodel.WeatherViewModel;
import com.example.weather.weather.viewmodel.WeatherViewModelFactory;

public class WeatherActivity extends BaseActivity implements ISearchListener {

    private WeatherViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        WeatherViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(WeatherViewModel.class);

        ActivityWeatherBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_weather);
        binding.setViewModel(this.viewModel);
        binding.setLifecycleOwner(this);

        binding.searchCities.setSearchListener(this);

        viewModel.listCities.observe(this, resultNetwork -> {
            binding.progressBar.setVisibility(resultNetwork.getStatusProgressBar());
            switch (resultNetwork.status) {
                case LOADING:
                    break;
                case ERROR: {
                    if(resultNetwork.error != null)
                        showError(resultNetwork.error);
                    break;
                }
                case SUCCESS: {
                    if(resultNetwork.data != null)
                        binding.searchCities.setCities(resultNetwork.data);
                }
            }
        });

        viewModel.cityWeather.observe(this, resultNetwork -> {
            binding.progressBar.setVisibility(resultNetwork.getStatusProgressBar());
            if (resultNetwork.status == ResultType.ERROR && resultNetwork.error != null) {
                showError(resultNetwork.error);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSearchTextChanged(String query) {
        viewModel.updateCities(query);
    }

    @Override
    public void onSelectCity(Float lat, Float lng, String nameCity) {
        viewModel.loadCityInfo(lat, lng, nameCity);
    }
}
