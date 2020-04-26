package com.example.weather.weather.ui;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.weather.R;
import com.example.weather.components.cites_saerch_view.ISearchListener;
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

        binding.searchCites.setSearchListener(this);

        viewModel.listCites.observe(this, resultNetwork -> {

            switch (resultNetwork.status) {
                case LOADING:
                    binding.progressBar.setVisibility(View.VISIBLE);
                    break;
                case ERROR: {
                    if(resultNetwork.error != null)
                        showError(resultNetwork.error);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    break;
                }
                case SUCCESS: {
                    if(resultNetwork.data != null)
                        binding.searchCites.setCites(resultNetwork.data);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

        viewModel.cityWeather.observe(this, resultNetwork -> {
            binding.progressBar.setVisibility(resultNetwork.status == ResultType.LOADING ? View.VISIBLE : View.INVISIBLE  );
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
        viewModel.updateCites(query);
    }

    @Override
    public void onSelectCity(Float lat, Float lng, String nameCity) {
        viewModel.loadCityInfo(lat, lng, nameCity);
    }
}
