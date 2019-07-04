package com.sample.triphistory.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.sample.triphistory.model.TripHistory;
import com.sample.triphistory.model.TripHistoryRepository;

public class TripHistoryListViewModel extends AndroidViewModel {

    private final TripHistoryRepository repository;
    private Context context;

    TripHistoryListViewModel(@NonNull Application application, @NonNull TripHistoryRepository repository) {
        super(application);
        context = application;
        this.repository = repository;
    }

    public TripHistory getTripHistory() {
        return repository.getTripHistoryList(context);
    }
}
