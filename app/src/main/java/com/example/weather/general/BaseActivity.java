package com.example.weather.general;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    protected void showError(Throwable error){
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
    }
    protected void showError(String error){
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
