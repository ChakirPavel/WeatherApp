package com.example.weather.components.cites_saerch_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.util.AttributeSet;
import android.widget.CursorAdapter;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import com.example.weather.model.data.city.City;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

public class CitesSearchView extends SearchView implements SearchView.OnSuggestionListener{

    List<City> cites = Collections.emptyList();
    private SimpleCursorAdapter suggestionAdapter;
    private ISearchListener searchListener = null;

    public CitesSearchView(Context context) {
        super(context);
        init();
    }

    public CitesSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CitesSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
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

        Observable.create((ObservableOnSubscribe<String>) emitter -> setOnQueryTextListener(new OnQueryTextListener() {
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
    }

    public void setCites(List<City> cites) {
        this.cites = cites;
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
        this.setQuery(cites.get(position).formatted_address, true);
        if (this.searchListener != null)
            this.searchListener.onSelectCity(cites.get(position).geometry.location.lat, cites.get(position).geometry.location.lng, cites.get(position).formatted_address);
        return true;
    }

    private void showSuggestion() {
        final MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID, "cityName"});
        for (int i = 0; i < cites.size(); i++)
            c.addRow(new Object[]{i, cites.get(i).formatted_address});

        suggestionAdapter.changeCursor(c);
    }
    private void hideSuggestion() {
        final MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID, "cityName"});
        suggestionAdapter.changeCursor(c);
    }
}
