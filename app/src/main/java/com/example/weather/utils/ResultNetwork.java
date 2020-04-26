package com.example.weather.utils;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ResultNetwork<T> {


    public final ResultType status;

    @Nullable
    public final T data;

    @Nullable
    public final Throwable error;

    private ResultNetwork(ResultType status, @Nullable T data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public int getStatusProgressBar(){
        return status == ResultType.LOADING ? View.VISIBLE : View.INVISIBLE;
    }

    public static ResultNetwork loading() {
        return new ResultNetwork(ResultType.LOADING, null, null);
    }

    public static <T>ResultNetwork success(T data) {
        return new ResultNetwork(ResultType.SUCCESS, data, null);
    }

    public static ResultNetwork error(@NonNull Throwable error) {
        return new ResultNetwork(ResultType.ERROR, null, error);
    }


}


