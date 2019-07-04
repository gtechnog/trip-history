package com.sample.triphistory.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sample.triphistory.model.TripHistoryRepository;

public class TripDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Application application;
    private final TripHistoryRepository repository;
    private final String tripId;

    public TripDetailViewModelFactory(@NonNull Application application,
                                      @NonNull TripHistoryRepository repository,
                                      @NonNull String tripId) {

        this.application = application;
        this.repository = repository;
        this.tripId = tripId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TripDetailViewModel(application, repository, tripId);
    }
}
