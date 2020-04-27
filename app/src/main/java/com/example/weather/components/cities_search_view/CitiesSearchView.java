package com.example.weather.components.cities_search_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import com.example.weather.model.data.city.City;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CitiesSearchView extends SearchView implements SearchView.OnSuggestionListener{

    List<City> cities = Collections.emptyList();
    private SimpleCursorAdapter suggestionAdapter;
    private ISearchListener searchListener = null;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public CitiesSearchView(Context context) {
        super(context);
        init();
    }

    public CitiesSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CitiesSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("CheckResult")
    void init() {
        this.setIconifiedByDefault(false);
        suggestionAdapter = new SimpleCursorAdapter(this.getContext(),
                android.R.layout.simple_list_item_1,
                null,
                new String[]{"cityName"},
                new int[]{android.R.id.text1},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        this.setSuggestionsAdapter(suggestionAdapter);
        this.setOnSuggestionListener(this);

        initObserveQuery();
    }

    private void initObserveQuery(){
        Disposable disposable = Observable.create((ObservableOnSubscribe<String>) emitter -> setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                emitter.onNext(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                emitter.onNext(newText);
                hideSuggestion();
                return false;
            }
        }))
                .map(x -> x.toLowerCase().trim())
                .debounce(500, TimeUnit.MILLISECONDS)
                .distinct()
                .filter(text -> text.length() >= 2)
                .subscribe(text -> searchListener.onSearchTextChanged(text));

        compositeDisposable.add(disposable);
    }
    
    public void setCities(List<City> cities) {
        this.cities = cities;
        showSuggestion();
    }

    public void setSearchListener(ISearchListener listener) {
        this.searchListener = listener;
    }


    @Override
    public boolean onSuggestionSelect(int position) {
        return true;
    }

    @Override
    public boolean onSuggestionClick(int position) {
        this.setQuery(cities.get(position).formatted_address, true);
        if (this.searchListener != null)
            this.searchListener.onSelectCity(cities.get(position).geometry.location.lat, cities.get(position).geometry.location.lng, cities.get(position).formatted_address);
        return true;
    }

    private void showSuggestion() {
        final MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID, "cityName"});
        for (int i = 0; i < cities.size(); i++)
            c.addRow(new Object[]{i, cities.get(i).formatted_address});

        suggestionAdapter.changeCursor(c);
    }
    private void hideSuggestion() {
        final MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID, "cityName"});
        suggestionAdapter.changeCursor(c);
    }


}
