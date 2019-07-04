package com.sample.triphistory.utils;

import android.app.Application;

import androidx.annotation.NonNull;

import com.sample.triphistory.model.TripHistoryRepository;
import com.sample.triphistory.viewmodel.TripDetailViewModelFactory;
import com.sample.triphistory.viewmodel.TripHistoryListViewModelFactory;

public class InjectorUtils {

    public static TripDetailViewModelFactory provideTripDetailViewModelFactory(@NonNull Application application, @NonNull String tripId) {
        return new TripDetailViewModelFactory(application, getTripHistoryRepository(), tripId);
    }

    public static TripHistoryListViewModelFactory provideTripHistoryListViewModelFactory(@NonNull Application application) {
        return new TripHistoryListViewModelFactory(application, getTripHistoryRepository());
    }

    private static TripHistoryRepository getTripHistoryRepository() {
        return TripHistoryRepository.getInstance();
    }
}
