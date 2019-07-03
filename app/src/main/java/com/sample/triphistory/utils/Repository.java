package com.sample.triphistory.utils;

import com.sample.triphistory.model.TripHistoryRepository;

public class Repository {

    public static TripHistoryRepository getTripHistoryRepository() {
        return TripHistoryRepository.getInstance();
    }
}
