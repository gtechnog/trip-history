package com.sample.triphistory.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sample.triphistory.model.TripHistoryRepository;

public class TripHistoryListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Application application;
    private final TripHistoryRepository repository;

    public TripHistoryListViewModelFactory(@NonNull Application application, @NonNull TripHistoryRepository repository) {
        this.application = application;
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TripHistoryListViewModel(application, repository);
    }
}
