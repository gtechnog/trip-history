package com.sample.triphistory.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.sample.triphistory.model.TripHistory;
import com.sample.triphistory.utils.Repository;

public class TripHistoryListViewModel extends AndroidViewModel {

    private Context context;

    public TripHistoryListViewModel(@NonNull Application application) {
        super(application);
        context = application;
    }

    public TripHistory getTripHistory() {
        return Repository.getTripHistoryRepository().getTripHistoryList(context);
    }
}
